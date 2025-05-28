// src/app/features/producto/producto.component.ts

import { Component, OnInit } from '@angular/core';
import { ProductoService } from '../../core/facades/producto.service';
import { Producto } from '../../core/models/producto.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-producto',
    standalone: false,
  templateUrl: './producto.component.html',
  styleUrls: ['./producto.component.css']
})
export class ProductoComponent implements OnInit {
  lista: Producto[] = [];
  nuevo: Producto = {
    codigo: '',
    nombre: '',
    descripcion: '',
    precio: 0,
    codigoCategoria: '',
    codigoSubcategoria: ''
  };
  cargando = false;
  error = '';

  constructor(private svc: ProductoService) {}

  ngOnInit() {
    this.cargarProductos();
  }

  cargarProductos() {
    this.cargando = true;
    this.error = '';
    this.svc.getAll().subscribe({
      next: datos => {
        this.lista = datos;
        this.cargando = false;
      },
      error: err => {
        this.error = 'Error al cargar productos';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  guardar() {
    this.cargando = true;
    this.error = '';
    const peticion: Observable<any> = this.nuevo.codigo
      ? this.svc.update(this.nuevo.codigo, this.nuevo)
      : this.svc.create(this.nuevo);

    peticion.subscribe({
      next: () => {
        this.cargarProductos();
        this.nuevo = {
          codigo: '',
          nombre: '',
          descripcion: '',
          precio: 0,
          codigoCategoria: '',
          codigoSubcategoria: ''
        };
      },
      error: err => {
        this.error = 'Error al guardar el producto';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  eliminar(codigo: string) {
    if (!confirm('¿Seguro que deseas eliminar este producto?')) {
      return;
    }
    this.cargando = true;
    this.error = '';
    this.svc.delete(codigo).subscribe({
      next: () => this.cargarProductos(),
      error: err => {
        this.error = 'Error al eliminar el producto';
        console.error(err);
        this.cargando = false;
      }
    });
  }
}
