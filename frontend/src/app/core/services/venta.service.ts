import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Venta {
  codigo?: string;
  sesionTrabajo: any;
  usuario: any;
  // agrega los demás campos de la venta según tu backend (productos, total, etc)
}

@Injectable({ providedIn: 'root' })
export class VentaService {
  private apiUrl = '/api/ventas';

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
