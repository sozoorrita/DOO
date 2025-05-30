import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface Usuario {
  id?: string;
  nombre: string;
  codigoRol: string;
  contrasena: string;
}

@Injectable({ providedIn: 'root' })
export class UsuarioService {
  private apiUrl = '/api/usuarios';

  constructor(private http: HttpClient) {}

  getUsuarios(): Observable<Usuario[]> {
    return this.http.get<Usuario[]>(this.apiUrl);
  }

  registrar(usuario: Usuario): Observable<Usuario> {
    return this.http.post<Usuario>(this.apiUrl, usuario);
  }

  modificar(id: string, usuario: Usuario): Observable<Usuario> {
    return this.http.put<Usuario>(`${this.apiUrl}/${id}`, usuario);
  }

  eliminar(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  iniciarSesion(usuario: Usuario, tipoUsuario: string): Observable<void> {
    return this.http.post<void>(
      `${this.apiUrl}/iniciar-sesion?tipoUsuario=${tipoUsuario}`,
      usuario
    );
  }
}
