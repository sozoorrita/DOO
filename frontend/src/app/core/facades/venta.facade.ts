// src/app/core/facades/venta.facade.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Venta } from '../models/venta.model';

@Injectable({ providedIn: 'root' })
export class VentaFacade {
  private base = '/api/ventas';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Venta[]> {
    return this.http.get<Venta[]>(this.base);
  }

  getById(id: string): Observable<Venta> {
    return this.http.get<Venta>(`${this.base}/${id}`);
  }

  create(v: Venta): Observable<Venta> {
    return this.http.post<Venta>(this.base, v);
  }

  update(id: string, v: Venta): Observable<void> {
    return this.http.put<void>(`${this.base}/${id}`, v);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
