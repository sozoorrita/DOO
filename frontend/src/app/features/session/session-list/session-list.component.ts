import { Component, OnInit } from '@angular/core';
import { SessionService, WorkSession } from '../../../core/services/session.service';

@Component({
  selector: 'app-session-list',
  standalone: false,
  templateUrl: './session-list.component.html',
  styleUrls: ['./session-list.component.css']
})
export class SessionListComponent implements OnInit {
  sessions: WorkSession[] = [];

  constructor(private sessionService: SessionService) {}

  ngOnInit(): void {
    this.loadSessions();
  }

  loadSessions(): void {
    this.sessionService.getSessions().subscribe(data => this.sessions = data);
  }

  get hasActiveSession(): boolean {
    return this.sessions.some(s => s.status === 'Abierta');
  }

  openSession(): void {
    this.sessionService.openSession();
    this.loadSessions();
  }

  closeSession(): void {
    this.sessionService.closeCurrentSession();
    this.loadSessions();
  }
}
