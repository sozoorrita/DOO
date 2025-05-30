import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface EstadoMesa {
  codigo?: string;
  nombre: string;
  // otros campos según tu backend
}

@Injectable({ providedIn: 'root' })
export class EstadoMesaService {
  private apiUrl = '/api/v1/estado-mesas';

  constructor(private http: HttpClient) {}

  getEstadoMesas(): Observable<EstadoMesa[]> {
    return this.http.get<EstadoMesa[]>(this.apiUrl);
  }

  getEstadoMesaPorId(codigo: string): Observable<EstadoMesa> {
    return this.http.get<EstadoMesa>(`${this.apiUrl}/${codigo}`);
  }

  registrar(estado: EstadoMesa): Observable<EstadoMesa> {
    return this.http.post<EstadoMesa>(this.apiUrl, estado);
  }

  modificar(codigo: string, estado: EstadoMesa): Observable<EstadoMesa> {
    return this.http.put<EstadoMesa>(`${this.apiUrl}/${codigo}`, estado);
  }

  eliminar(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}
