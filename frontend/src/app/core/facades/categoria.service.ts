import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Categoria } from '../models/categoria.model';

@Injectable({ providedIn: 'root' })
export class CategoriaService {
  private url = `${environment.apiUrl}/categorias`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.url);
  }

  getByFilter(filtro: Partial<Categoria>): Observable<Categoria[]> {
    return this.http.post<Categoria[]>(`${this.url}/filter`, filtro);
  }

  create(item: Categoria): Observable<Categoria> {
    return this.http.post<Categoria>(this.url, item);
  }

  update(codigo: string, item: Categoria): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
