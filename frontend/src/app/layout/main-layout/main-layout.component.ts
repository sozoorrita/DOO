// src/app/layout/main-layout/main-layout.component.ts

import { Component } from '@angular/core';
import { Router }    from '@angular/router';
import { AuthService }    from '../../core/services/auth.service';
import { SessionService } from '../../core/services/session.service';

@Component({
  selector: 'app-main-layout',
  standalone: false,
  templateUrl: './main-layout.component.html',
  styleUrls: ['./main-layout.component.css']
})
export class MainLayoutComponent {
  constructor(
    private auth: AuthService,
    private session: SessionService,
    private router: Router
  ) {}

  // Getter reactivo para el template
  get hasActiveSession(): boolean {
    return this.session.hasActiveSession();
  }

  // logout usuario
  logoutUser() {
    this.auth.logout();
    this.router.navigate(['/auth/login']);
  }
}
