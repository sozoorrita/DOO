import { Injectable } from '@angular/core';
import { CanActivate, Router }    from '@angular/router';
import { SessionService }         from '../core/services/session.service';

@Injectable({ providedIn: 'root' })
export class SessionGuard implements CanActivate {
  constructor(private session: SessionService, private router: Router) {}

  canActivate(): boolean {
    if (this.session.hasActiveSession()) {
      return true;
    }
    // si no hay sesión abierta, vamos primero a la lista
    this.router.navigate(['/session/list']);
    return false;
  }
}
