import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { IndicadorInventario } from '../models/indicador-inventario.model';

@Injectable({ providedIn: 'root' })
export class IndicadorInventarioService {
  private url = `${environment.apiUrl}/indicador-inventarios`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<IndicadorInventario[]> {
    return this.http.get<IndicadorInventario[]>(this.url);
  }

  create(item: IndicadorInventario): Observable<IndicadorInventario> {
    return this.http.post<IndicadorInventario>(this.url, item);
  }

  update(codigo: string, item: IndicadorInventario): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
