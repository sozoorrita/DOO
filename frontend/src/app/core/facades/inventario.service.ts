import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Inventario } from '../models/inventario.model';

@Injectable({ providedIn: 'root' })
export class InventarioService {
  private url = `${environment.apiUrl}/inventarios`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Inventario[]> {
    return this.http.get<Inventario[]>(this.url);
  }

  create(item: Inventario): Observable<Inventario> {
    return this.http.post<Inventario>(this.url, item);
  }

  update(codigo: string, item: Inventario): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
