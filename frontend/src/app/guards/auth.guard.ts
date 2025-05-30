import { Injectable } from '@angular/core';
import { CanActivate, Router, UrlTree } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(): boolean | UrlTree {
    // Puedes mejorar esto usando un servicio de Auth real
    const usuario = localStorage.getItem('usuario');
    if (usuario) {
      return true;
    }
    // Redirige al login si no está autenticado
    return this.router.createUrlTree(['/login']);
  }
}
