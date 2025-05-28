import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { SesionTrabajo } from '../models/sesion-trabajo.model';

@Injectable({ providedIn: 'root' })
export class SesionTrabajoService {
  private url = `${environment.apiUrl}/sesion-trabajos`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<SesionTrabajo[]> {
    return this.http.get<SesionTrabajo[]>(this.url);
  }

  create(item: SesionTrabajo): Observable<SesionTrabajo> {
    return this.http.post<SesionTrabajo>(this.url, item);
  }

  update(codigo: string, item: SesionTrabajo): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
