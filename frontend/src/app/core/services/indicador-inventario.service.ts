import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface IndicadorInventario {
  codigo?: string;
  nombre: string;
  // otros campos
}

@Injectable({ providedIn: 'root' })
export class IndicadorInventarioService {
  private apiUrl = '/api/v1/indicador-inventarios';

  constructor(private http: HttpClient) {}

  getIndicadorInventarios(): Observable<IndicadorInventario[]> {
    return this.http.get<IndicadorInventario[]>(this.apiUrl);
  }

  getIndicadorInventarioPorId(codigo: string): Observable<IndicadorInventario> {
    return this.http.get<IndicadorInventario>(`${this.apiUrl}/${codigo}`);
  }

  registrar(indicador: IndicadorInventario): Observable<IndicadorInventario> {
    return this.http.post<IndicadorInventario>(this.apiUrl, indicador);
  }

  modificar(codigo: string, indicador: IndicadorInventario): Observable<IndicadorInventario> {
    return this.http.put<IndicadorInventario>(`${this.apiUrl}/${codigo}`, indicador);
  }

  eliminar(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}
