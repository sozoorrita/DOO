// src/app/core/facades/session.facade.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Session } from '../models/session.model';

interface Report { total: number; }

@Injectable({ providedIn: 'root' })
export class SessionFacade {
  private base = '/api/sessions';

  constructor(private http: HttpClient) {}

  open(payload: { usuarioId: string; baseCaja: number }): Observable<Session> {
    return this.http.post<Session>(`${this.base}/open`, payload);
  }

  close(): Observable<void> {
    return this.http.post<void>(`${this.base}/close`, {});
  }

  getReport(): Observable<Report> {
    return this.http.get<Report>(`${this.base}/report`);
  }
}
