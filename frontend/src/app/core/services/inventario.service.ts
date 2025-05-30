import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Inventario {
  codigo?: string;
  producto: string;
  cantidad: number;
  codigoIndicador: string;
}

@Injectable({ providedIn: 'root' })
export class InventarioService {
  private apiUrl = '/api/v1/inventarios';

  constructor(private http: HttpClient) {}

  getInventarios(): Observable<Inventario[]> {
    return this.http.get<Inventario[]>(this.apiUrl);
  }

  getInventarioPorId(codigo: string): Observable<Inventario> {
    return this.http.get<Inventario>(`${this.apiUrl}/${codigo}`);
  }

  registrar(inventario: Inventario): Observable<Inventario> {
    return this.http.post<Inventario>(this.apiUrl, inventario);
  }

  modificar(codigo: string, inventario: Inventario): Observable<Inventario> {
    return this.http.put<Inventario>(`${this.apiUrl}/${codigo}`, inventario);
  }

  eliminar(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}
