// src/app/guards/auth.guard.ts

import { Injectable } from '@angular/core';
import {
  CanActivate,
  Router,
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  UrlTree
} from '@angular/router';
import { AuthService } from '../core/services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree {
    // Si hay sesión activa → permitimos acceso
    if (this.authService.isLoggedIn()) {
      return true;
    }
    // Si no hay sesión → redirigimos a /auth/login
    return this.router.parseUrl('/auth/login');
  }
}
