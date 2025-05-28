// src/app/core/facades/usuario.facade.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Usuario } from '../models/usuario.model';

@Injectable({ providedIn: 'root' })
export class UsuarioFacade {
  private base = '/api/usuarios';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.base);
  }

  getById(id: string): Observable<Usuario> {
    return this.http.get<Usuario>(`${this.base}/${id}`);
  }

  create(u: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.base, u);
  }

  update(id: string, u: Usuario): Observable<void> {
    return this.http.put<void>(`${this.base}/${id}`, u);
  }

  delete(id: string): Observable<void> {
    return this.http.delete<void>(`${this.base}/${id}`);
  }
}
