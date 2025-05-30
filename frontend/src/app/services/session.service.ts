import { Injectable } from '@angular/core';
import { sessions, Session } from '../../mockData/sessions';
import { Observable, of } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class SessionService {
  getById(id: string): Observable<Session | undefined> {
    return of(sessions.find(s => s.id === id));
  }

  getAll(): Observable<Session[]> {
    return of(sessions);
  }
}