import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SessionStateService } from '../core/services/session-state.service';

interface SessionRecord {
  startDate: string;
  endDate: string;
  total: number;
  status: 'Abierta' | 'Cerrada';
}

@Component({
  selector: 'app-open-session',
  standalone: false,
  templateUrl: './open-session.component.html',
  styleUrls: ['./open-session.component.css']
})
export class OpenSessionComponent implements OnInit {
  public isSessionOpen = false;
  public sessionHistory: SessionRecord[] = [ /* ...mock data... */ ];

  constructor(
    private sessionState: SessionStateService,
    private router: Router
  ) {}

  ngOnInit(): void {}

  public openSession(): void {
    this.sessionState.isOpen = true;
    this.router.navigate(['/tables']);
  }

  // Este método es sólo para volver desde tables si lo quisieras aquí,
  // pero normalmente lo manejas en TableMapComponent.
  public closeSession(): void {
    this.sessionState.isOpen = false;
    this.router.navigate(['/session']);
  }
}
