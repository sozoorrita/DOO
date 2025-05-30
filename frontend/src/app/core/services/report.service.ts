import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { WorkSession, SessionService } from './session.service';

export interface ReporteCaja {
  sessionId: string;
  start: Date;
  end: Date;
  total: number;
  cash: number;
  transfer: number;
}

@Injectable({ providedIn: 'root' })
export class ReportService {
  constructor(private sessionService: SessionService) {}

  getClosedSessions(): Observable<WorkSession[]> {
    return this.sessionService.getSessions().pipe(
      map(s => s.filter(x => x.status === 'Cerrada'))
    );
  }

  getReport(sessionId: string): Observable<ReporteCaja> {
    // mock sencillo
    return of({
      sessionId,
      start: new Date('2024-04-01T08:00'),
      end:   new Date('2024-04-01T16:00'),
      total: 125000,
      cash:  75000,
      transfer: 50000
    });
  }
}
