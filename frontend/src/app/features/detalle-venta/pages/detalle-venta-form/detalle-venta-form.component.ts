// src/app/features/detalle-venta/pages/detalle-venta-form/detalle-venta-form.component.ts

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { DetalleVenta } from 'src/app/core/models/detalle-venta.model';
import { DetalleVentaService } from 'src/app/core/services/detalle-venta.service';

@Component({
  selector: 'app-detalle-venta-form',
  standalone : false,
  templateUrl: './detalle-venta-form.component.html',
  styleUrls: ['./detalle-venta-form.component.css']
})
export class DetalleVentaFormComponent implements OnInit {
  form!: FormGroup;
  codigo?: string;
  cargando = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private svc: DetalleVentaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      codigoVenta: ['', Validators.required],
      codigoProducto: ['', Validators.required],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      precioAplicado: [0, [Validators.required, Validators.min(0)]]
    });

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.codigo = id;
        this.cargarDetalle(id);
      }
    });
  }

  cargarDetalle(id: string): void {
    this.cargando = true;
    this.svc.listar().subscribe({
      next: all => {
        const det = all.find(d => d.codigo === id) as DetalleVenta;
        if (det) {
          this.form.patchValue({
            codigoVenta: det.codigoVenta,
            codigoProducto: det.codigoProducto,
            cantidad: det.cantidad,
            precioAplicado: det.precioAplicado
          });
        }
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.error = 'No se pudo cargar el detalle';
        this.cargando = false;
      }
    });
  }

  submit(): void {
    if (this.form.invalid) { return; }
    this.cargando = true;
    const dato: DetalleVenta = { ...this.form.value };
    const call$ = this.codigo
      ? this.svc.actualizar(this.codigo, dato)
      : this.svc.crear(dato);

    call$.subscribe({
      next: () => this.router.navigate(['detalle-ventas']),
      error: err => {
        console.error(err);
        this.error = 'Error al guardar';
        this.cargando = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['detalle-ventas']);
  }
}
