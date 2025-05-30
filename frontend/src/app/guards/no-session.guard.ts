import { Injectable } from '@angular/core';
import { CanActivate, Router }    from '@angular/router';
import { SessionService }         from '../core/services/session.service';

@Injectable({ providedIn: 'root' })
export class NoSessionGuard implements CanActivate {
  constructor(private session: SessionService, private router: Router) {}

  canActivate(): boolean {
    if (!this.session.hasActiveSession()) {
      return true;
    }
    // si hay sesión abierta, volvemos a mesas
    this.router.navigate(['/mesas']);
    return false;
  }
}
