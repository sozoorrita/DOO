// src/app/core/facades/producto.facade.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Producto } from '../models/producto.model';

@Injectable({ providedIn: 'root' })
export class ProductoFacade {
  private base = '/api/productos';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.base);
  }

  getById(id: string): Observable<Producto> {
    return this.http.get<Producto>(`${this.base}/${id}`);
  }

  create(p: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.base, p);
  }

  update(id: string, p: Producto): Observable<void> {
    return this.http.put<void>(`${this.base}/${id}`, p);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
