import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

export interface WorkSession {
  id: string;
  start: Date;
  end?: Date;
  totalSales: number;
  status: 'Abierta' | 'Cerrada';
}

// Inicialmente no hay sesiones abiertas ni cerradas
const initialSessions: WorkSession[] = [];

@Injectable({ providedIn: 'root' })
export class SessionService {
  private sessionsSubject = new BehaviorSubject<WorkSession[]>(initialSessions);

  getSessions(): Observable<WorkSession[]> {
    return this.sessionsSubject.asObservable();
  }

  openSession(): void {
    const now = new Date();
    const newSession: WorkSession = {
      id: now.getTime().toString(),
      start: now,
      end: undefined,
      totalSales: 0,
      status: 'Abierta'
    };
    this.sessionsSubject.next([newSession, ...this.sessionsSubject.getValue()]);
  }

  closeCurrentSession(): void {
    const sessions = this.sessionsSubject.getValue().map(s => {
      if (s.status === 'Abierta') {
        return { ...s, status: 'Cerrada', end: new Date() } as WorkSession;
      }
      return s;
    });
    this.sessionsSubject.next(sessions);
  }

  hasActiveSession(): boolean {
    return this.sessionsSubject.getValue().some(s => s.status === 'Abierta');
  }
}
