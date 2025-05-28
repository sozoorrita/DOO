// src/app/core/facades/inventario.facade.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Categoria } from '../models/categoria.model';
import { Subcategoria } from '../models/subcategoria.model';
import { Producto } from '../models/producto.model';

@Injectable({ providedIn: 'root' })
export class InventarioFacade {
  private base = '/api/inventario';

  constructor(private http: HttpClient) {}

  getCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(`${this.base}/categorias`);
  }

  getSubcategorias(catId: string): Observable<Subcategoria[]> {
    return this.http.get<Subcategoria[]>(`${this.base}/categorias/${catId}/subcategorias`);
  }

  getProductos(subId: string): Observable<Producto[]> {
    return this.http.get<Producto[]>(`${this.base}/subcategorias/${subId}/productos`);
  }
}
