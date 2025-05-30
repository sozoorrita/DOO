import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface TipoVenta {
  codigo?: string;
  nombre: string;
}

@Injectable({ providedIn: 'root' })
export class TipoVentaService {
  private apiUrl = '/api/v1/tipo-ventas';

  constructor(private http: HttpClient) {}

  getTipoVentas(): Observable<TipoVenta[]> {
    return this.http.get<TipoVenta[]>(this.apiUrl);
  }

  getTipoVentaPorId(codigo: string): Observable<TipoVenta> {
    return this.http.get<TipoVenta>(`${this.apiUrl}/${codigo}`);
  }

  registrar(tipo: TipoVenta): Observable<TipoVenta> {
    return this.http.post<TipoVenta>(this.apiUrl, tipo);
  }

  modificar(codigo: string, tipo: TipoVenta): Observable<TipoVenta> {
    return this.http.put<TipoVenta>(`${this.apiUrl}/${codigo}`, tipo);
  }

  eliminar(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}
