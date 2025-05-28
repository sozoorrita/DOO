import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { EstadoMesa } from '../models/estado-mesa.model';

@Injectable({ providedIn: 'root' })
export class EstadoMesaService {
  private url = `${environment.apiUrl}/estado-mesas`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<EstadoMesa[]> {
    return this.http.get<EstadoMesa[]>(this.url);
  }

  create(item: EstadoMesa): Observable<EstadoMesa> {
    return this.http.post<EstadoMesa>(this.url, item);
  }

  update(codigo: string, item: EstadoMesa): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
