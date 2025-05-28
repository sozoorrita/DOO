// src/app/features/forma-pago/pages/forma-pago-form/forma-pago-form.component.ts
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { FormaPagoService } from 'src/app/core/services/forma-pago.service';

@Component({
  selector: 'app-forma-pago-form',
  standalone : false,
  templateUrl: './forma-pago-form.component.html',
  styleUrls: ['./forma-pago-form.component.css']
})
export class FormaPagoFormComponent implements OnInit {
  form!: FormGroup;
  codigo?: string;
  cargando = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private svc: FormaPagoService,
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
        this.cargarForma(id);
      }
    });
  }

  cargarForma(codigo: string): void {
    this.cargando = true;
    this.svc.listar().subscribe({
      next: all => {
        const f = all.find(x => x.codigo === codigo);
        if (f) this.form.patchValue({ nombre: f.nombre });
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Error al cargar';
        this.cargando = false;
      }
    });
  }

  submit(): void {
    if (this.form.invalid) return;
    this.cargando = true;
    const nombre = this.form.value.nombre as string;
    const call$ = this.codigo
      ? this.svc.actualizar(this.codigo, nombre)
      : this.svc.crear(nombre);

    call$.subscribe({
      next: () => this.router.navigate(['formas-pago']),
      error: err => {
        console.error(err);
        this.error = 'Error al guardar';
        this.cargando = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['formas-pago']);
  }
}
