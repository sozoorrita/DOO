import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Categoria {
  codigo?: string;
  nombre: string;
  // agrega otros campos según tu backend
}

@Injectable({ providedIn: 'root' })
export class CategoriaService {
  private apiUrl = '/api/categorias';

  constructor(private http: HttpClient) {}

  getCategorias(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.apiUrl);
  }

  getCategoriaPorId(codigo: string): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.apiUrl}/${codigo}`);
  }

  registrar(categoria: Categoria): Observable<Categoria> {
    return this.http.post<Categoria>(this.apiUrl, categoria);
  }

  modificar(codigo: string, categoria: Categoria): Observable<Categoria> {
    return this.http.put<Categoria>(`${this.apiUrl}/${codigo}`, categoria);
  }

  eliminar(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${codigo}`);
  }
}
