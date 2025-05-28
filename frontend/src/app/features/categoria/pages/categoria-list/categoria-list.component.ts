// src/app/features/categoria/pages/categoria-list/categoria-list.component.ts

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Categoria } from 'src/app/core/models/categoria.model';
import { CategoriaService } from 'src/app/core/services/categoria.service';

@Component({
  selector: 'app-categoria-list',
  standalone: false,
  templateUrl: './categoria-list.component.html',
  styleUrls: ['./categoria-list.component.css']
})
export class CategoriaListComponent implements OnInit {
  categorias: Categoria[] = [];
  cargando = false;
  error = '';

  constructor(
    private categoriaService: CategoriaService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.obtenerListado();
  }

  obtenerListado(): void {
    this.cargando = true;
    this.categoriaService.listar().subscribe({
      next: data => {
        this.categorias = data;
        this.cargando = false;
      },
      error: err => {
        console.error(err);
        this.error = 'Error al cargar categorías';
        this.cargando = false;
      }
    });
  }

  onNuevo(): void {
    this.router.navigate(['categorias', 'nuevo']);
  }

  onEditar(codigo: string): void {
    this.router.navigate(['categorias', 'editar', codigo]);
  }

  onEliminar(codigo: string): void {
    if (!confirm('¿Seguro que deseas eliminar esta categoría?')) {
      return;
    }
    this.categoriaService.eliminar(codigo).subscribe({
      next: () => this.obtenerListado(),
      error: err => {
        console.error(err);
        this.error = 'Error al eliminar';
      }
    });
  }
}
