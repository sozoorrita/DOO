import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Venta {
  codigo?: string;
  fecha?: string;
  totalVenta: number;
  codigoFormaPago: string;
  codigoTipoVenta: string;
  codigoSesionTrabajo: string;
  codigoMesa: string;
}


@Injectable({ providedIn: 'root' })
export class VentaService {
  private apiUrl = '/api/v1/ventas';

  constructor(private http: HttpClient) {}

  registrarVenta(venta: Venta): Observable<Venta> {
    return this.http.post<Venta>(this.apiUrl, venta);
  }

  getVentas(): Observable<Venta[]> {
    return this.http.get<Venta[]>(this.apiUrl);
  }

  getVentaPorId(codigo: string): Observable<Venta> {
    return this.http.get<Venta>(`${this.apiUrl}/${codigo}`);
  }
}
