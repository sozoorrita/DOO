import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SesionTrabajo {
  codigo?: string;
  usuario: any;
  fechaApertura?: string;
  fechaCierre?: string;
  estado: string;
}

@Injectable({ providedIn: 'root' })
export class SesionTrabajoService {
  private apiUrl = '/api/sesion-trabajo';

  constructor(private http: HttpClient) {}

  abrirSesion(sesion: SesionTrabajo): Observable<SesionTrabajo> {
    return this.http.post<SesionTrabajo>(this.apiUrl, sesion);
  }

  cerrarSesion(codigo: string): Observable<SesionTrabajo> {
    return this.http.put<SesionTrabajo>(`${this.apiUrl}/cerrar/${codigo}`, {});
  }

  getSesiones(): Observable<SesionTrabajo[]> {
    return this.http.get<SesionTrabajo[]>(this.apiUrl);
  }
}
