import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Subcategoria {
  codigo?: string;
  nombre: string;
  codigoCategoria: string; // <- usa camelCase igual que tu DTO y JSON del backend
}


@Injectable({ providedIn: 'root' })
export class SubcategoriaService {
  private apiUrl = '/api/v1/subcategorias';

  constructor(private http: HttpClient) {}

  getSubcategorias(): Observable<Subcategoria[]> {
    return this.http.get<Subcategoria[]>(this.apiUrl);
  }

  getSubcategoriaPorId(codigo: string): Observable<Subcategoria> {
    return this.http.get<Subcategoria>(`${this.apiUrl}/${codigo}`);
  }

  registrar(subcategoria: Subcategoria): Observable<Subcategoria> {
    return this.http.post<Subcategoria>(this.apiUrl, subcategoria);
  }

  modificar(codigo: string, subcategoria: Subcategoria): Observable<Subcategoria> {
    return this.http.put<Subcategoria>(`${this.apiUrl}/${codigo}`, subcategoria);
  }

  eliminar(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}
