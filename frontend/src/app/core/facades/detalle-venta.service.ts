import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { DetalleVenta } from '../models/detalle-venta.model';

@Injectable({ providedIn: 'root' })
export class DetalleVentaService {
  private url = `${environment.apiUrl}/detalle-ventas`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<DetalleVenta[]> {
    return this.http.get<DetalleVenta[]>(this.url);
  }

  create(item: DetalleVenta): Observable<DetalleVenta> {
    return this.http.post<DetalleVenta>(this.url, item);
  }

  update(codigo: string, item: DetalleVenta): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
