import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Observable } from 'rxjs';
import { Mesa } from '../models/mesa.model';

@Injectable({ providedIn: 'root' })
export class MesaService {
  private url = `${environment.apiUrl}/mesas`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<Mesa[]> {
    return this.http.get<Mesa[]>(this.url);
  }

  create(item: Mesa): Observable<Mesa> {
    return this.http.post<Mesa>(this.url, item);
  }

  update(codigo: string, item: Mesa): Observable<void> {
    return this.http.put<void>(`${this.url}/${codigo}`, item);
  }

  delete(codigo: string): Observable<void> {
    return this.http.delete<void>(`${this.url}/${codigo}`);
  }
}
