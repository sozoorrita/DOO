// src/app/core/facades/venta.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Venta } from '../models/venta.model';

@Injectable({ providedIn: 'root' })
export class VentaService {
  private url = `${environment.apiUrl}/ventas`;

  constructor(private http: HttpClient) {}

  create(item: Venta): Observable<Venta> {
    return this.http.post<Venta>(this.url, item);
  }

  update(codigo: string, item: Venta): Observable<Venta> {
    return this.http.put<Venta>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }

  getAll(): Observable<Venta[]> {
    return this.http.get<Venta[]>(this.url);
  }
}
