import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { AuthService } from '../core/services/auth.service';

@Injectable({ providedIn: 'root' })
export class SessionGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean | UrlTree {
    if (this.authService.isLoggedIn()) {
      // Si hay sesión activa, permite el acceso
      return true;
    } else {
      // Si NO hay sesión, y NO estamos ya en login, redirige
      if (state.url !== '/auth/login') {
        return this.router.parseUrl('/auth/login');
      }
      // Si ya estamos en login, permite el acceso a esa ruta
      return true;
    }
  }
}
