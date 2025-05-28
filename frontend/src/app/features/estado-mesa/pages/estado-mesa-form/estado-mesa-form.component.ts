// src/app/features/estado-mesa/pages/estado-mesa-form/estado-mesa-form.component.ts

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { EstadoMesaService } from 'src/app/core/services/estado-mesa.service';

@Component({
  selector: 'app-estado-mesa-form',
  standalone : false,
  templateUrl: './estado-mesa-form.component.html',
  styleUrls: ['./estado-mesa-form.component.css']
})
export class EstadoMesaFormComponent implements OnInit {
  form!: FormGroup;
  codigo?: string;
  cargando = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private svc: EstadoMesaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      nombre: ['', [Validators.required, Validators.maxLength(50)]]
    });

    this.route.paramMap.subscribe(params => {
      const id = params.get('id');
      if (id) {
        this.codigo = id;
        this.cargarEstado(id);
      }
    });
  }

  cargarEstado(codigo: string): void {
    this.cargando = true;
    this.svc.listar().subscribe({
      next: all => {
        const est = all.find(e => e.codigo === codigo);
        if (est) {
          this.form.patchValue({ nombre: est.nombre });
        }
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Error al cargar el estado';
        this.cargando = false;
      }
    });
  }

  submit(): void {
    if (this.form.invalid) {
      return;
    }
    this.cargando = true;
    const nombre = this.form.value.nombre as string;
    const call$ = this.codigo
      ? this.svc.actualizar(this.codigo, nombre)
      : this.svc.crear(nombre);

    call$.subscribe({
      next: () => this.router.navigate(['estado-mesas']),
      error: err => {
        console.error(err);
        this.error = 'Error al guardar';
        this.cargando = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['estado-mesas']);
  }
}
