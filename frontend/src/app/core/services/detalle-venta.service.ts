import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface DetalleVenta {
  codigo?: string;
  codigoventa: string;
  produc: string;
  cantidad: number;
  precioAplicado: number;
  // agrega otros campos según tu backend
}

@Injectable({ providedIn: 'root' })
export class DetalleVentaService {
  private apiUrl = '/api/v1/detalle-ventas';

  constructor(private http: HttpClient) {}

  getDetalleVentas(): Observable<DetalleVenta[]> {
    return this.http.get<DetalleVenta[]>(this.apiUrl);
  }

  getDetalleVentaPorId(codigo: string): Observable<DetalleVenta> {
    return this.http.get<DetalleVenta>(`${this.apiUrl}/${codigo}`);
  }

  registrar(detalle: DetalleVenta): Observable<DetalleVenta> {
    return this.http.post<DetalleVenta>(this.apiUrl, detalle);
  }

  modificar(codigo: string, detalle: DetalleVenta): Observable<DetalleVenta> {
    return this.http.put<DetalleVenta>(`${this.apiUrl}/${codigo}`, detalle);
  }

  eliminar(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}
