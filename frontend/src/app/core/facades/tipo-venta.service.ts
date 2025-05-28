import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { TipoVenta } from '../models/tipo-venta.model';

@Injectable({ providedIn: 'root' })
export class TipoVentaService {
  private url = `${environment.apiUrl}/tipo-ventas`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<TipoVenta[]> {
    return this.http.get<TipoVenta[]>(this.url);
  }

  create(item: TipoVenta): Observable<TipoVenta> {
    return this.http.post<TipoVenta>(this.url, item);
  }

  update(codigo: string, item: TipoVenta): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
