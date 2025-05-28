// src/app/features/indicador-inventario/pages/indicador-inventario-form/indicador-inventario-form.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { IndicadorInventarioService } from 'src/app/core/services/indicador-inventario.service';

@Component({
  selector: 'app-indicador-inventario-form',
  standalone : false,
  templateUrl: './indicador-inventario-form.component.html',
  styleUrls: ['./indicador-inventario-form.component.css']
})
export class IndicadorInventarioFormComponent implements OnInit {
  form!: FormGroup;
  codigo?: string;
  cargando = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private svc: IndicadorInventarioService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      codigoProducto: ['', Validators.required],
      cantidadActual: [0, [Validators.required, Validators.min(0)]],
      cantidadMinima: [0, [Validators.required, Validators.min(0)]],
      fechaMedicion: ['', Validators.required]
    });

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.codigo = id;
        this.cargarIndicador(id);
      }
    });
  }

  cargarIndicador(id: string): void {
    this.cargando = true;
    this.svc.listar().subscribe({
      next: all => {
        const ind = all.find(i => i.codigo === id);
        if (ind) {
          this.form.patchValue({
            codigoProducto: ind.codigoProducto,
            cantidadActual: ind.cantidadActual,
            cantidadMinima: ind.cantidadMinima,
            fechaMedicion: ind.fechaMedicion
          });
        }
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Error al cargar indicador';
        this.cargando = false;
      }
    });
  }

  submit(): void {
    if (this.form.invalid) { return; }
    this.cargando = true;
    const dto = this.form.value;
    const call$ = this.codigo
      ? this.svc.actualizar(this.codigo, dto)
      : this.svc.crear(dto);

    call$.subscribe({
      next: () => this.router.navigate(['indicador-inventario']),
      error: err => {
        console.error(err);
        this.error = 'Error al guardar';
        this.cargando = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['indicador-inventario']);
  }
}
