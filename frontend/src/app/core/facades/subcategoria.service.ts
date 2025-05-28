import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Subcategoria } from '../models/subcategoria.model';

@Injectable({ providedIn: 'root' })
export class SubcategoriaService {
  private url = `${environment.apiUrl}/subcategorias`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Subcategoria[]> {
    return this.http.get<Subcategoria[]>(this.url);
  }

  create(item: Subcategoria): Observable<Subcategoria> {
    return this.http.post<Subcategoria>(this.url, item);
  }

  update(codigo: string, item: Subcategoria): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
