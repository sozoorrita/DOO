import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, UrlTree } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class RoleGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean | UrlTree {
    const usuario = localStorage.getItem('usuario');
    if (!usuario) {
      return this.router.createUrlTree(['/login']);
    }
    const userObj = JSON.parse(usuario);
    // Suponiendo que tienes un campo codigoRol, compara con el rol requerido
    const requiredRole = route.data['role'];
    if (!requiredRole || userObj.codigoRol === requiredRole) {
      return true;
    }
    // Si no tiene el rol, lo puedes redirigir donde quieras
    return this.router.createUrlTree(['/']);
  }
}
