// src/app/core/services/categoria.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Categoria {
  codigo: string;
  nombre: string;
  fechaCreacion?: string;     // ← ahora opcional
  fechaEliminacion?: string;  // ← ahora opcional
}

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {
  private apiUrl = '/api/v1/categorias';

  constructor(private http: HttpClient) {}

  /** Listar todas las categorías (activas e inactivas) */
  listar(): Observable<Categoria[]> {
    return this.http.get<Categoria[]>(this.apiUrl);
  }

  /** Obtener una categoría por su ID */
  obtenerPorId(id: string): Observable<Categoria> {
    return this.http.get<Categoria>(`${this.apiUrl}/${id}`);
  }

  /** Crear una nueva categoría */
  crear(categoria: Partial<Categoria>): Observable<string> {
    return this.http.post<string>(
      this.apiUrl,
      categoria,
      {
        responseType: 'text' as 'json'
      }
    );
  }

  /** Actualizar una categoría existente */
  actualizar(id: string, categoria: Partial<Categoria>): Observable<string> {
    return this.http.put<string>(
      `${this.apiUrl}/${id}`,
      categoria,
      {
        responseType: 'text' as 'json'
      }
    );
  }

  /** Eliminar lógicamente una categoría (soft delete) */
  eliminar(id: string): Observable<string> {
    return this.http.delete<string>(
      `${this.apiUrl}/${id}`,
      {
        responseType: 'text' as 'json'
      }
    );
  }
}
