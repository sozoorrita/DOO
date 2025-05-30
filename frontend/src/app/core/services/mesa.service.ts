import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Mesa {
  codigo?: string;
  nombre: string;
  codigoEstadoMesa?: string;
}

@Injectable({ providedIn: 'root' })
export class MesaService {
  private apiUrl = '/api/v1/mesas';

  constructor(private http: HttpClient) {}

  getMesas(): Observable<Mesa[]> {
    return this.http.get<Mesa[]>(this.apiUrl);
  }

  getMesaPorId(codigo: string): Observable<Mesa> {
    return this.http.get<Mesa>(`${this.apiUrl}/${codigo}`);
  }

  registrar(mesa: Mesa): Observable<Mesa> {
    return this.http.post<Mesa>(this.apiUrl, mesa);
  }

  modificar(codigo: string, mesa: Mesa): Observable<Mesa> {
    return this.http.put<Mesa>(`${this.apiUrl}/${codigo}`, mesa);
  }

  eliminar(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}
