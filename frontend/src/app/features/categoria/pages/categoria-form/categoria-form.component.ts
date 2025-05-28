// src/app/features/categoria/pages/categoria-form/categoria-form.component.ts

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { Categoria } from 'src/app/core/models/categoria.model';
import { CategoriaService } from 'src/app/core/services/categoria.service';

@Component({
  selector: 'app-categoria-form',
  templateUrl: './categoria-form.component.html',
  standalone : false,
  styleUrls: ['./categoria-form.component.css']
})
export class CategoriaFormComponent implements OnInit {
  form!: FormGroup;
  codigo?: string;
  cargando = false;
  error = '';

  constructor(
    private fb: FormBuilder,
    private categoriaService: CategoriaService,
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
        this.cargarCategoria(id);
      }
    });
  }

  cargarCategoria(codigo: string): void {
    this.cargando = true;
    // Como el controlador usa GET sin path, usamos dummy + filtrar
    this.categoriaService.listar().subscribe({
      next: all => {
        const cat = all.find(c => c.codigo === codigo);
        if (cat) {
          this.form.patchValue({ nombre: cat.nombre });
        }
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.error = 'No se pudo cargar la categoría';
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

    const obs = this.codigo
      ? this.categoriaService.actualizar(this.codigo, nombre)
      : this.categoriaService.crear(nombre);

    obs.subscribe({
      next: () => this.router.navigate(['categorias']),
      error: err => {
        console.error(err);
        this.error = 'Error al guardar la categoría';
        this.cargando = false;
      }
    });
  }

  cancel(): void {
    this.router.navigate(['categorias']);
  }
}
