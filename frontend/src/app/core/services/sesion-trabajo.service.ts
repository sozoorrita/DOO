import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface SesionTrabajo {
  codigo?: string;           // UUID
  idUsuario: string;         // UUID de usuario
  baseCaja: string;          // BigDecimal, maneja como string (o number si lo parseas)
  fechaApertura?: string;    // LocalDateTime serializado como string
  fechaCierre?: string;      // LocalDateTime serializado como string (puede ser null)
}


@Injectable({ providedIn: 'root' })
export class SesionTrabajoService {
  private apiUrl = '/api/v1/sesion-trabajo';

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
