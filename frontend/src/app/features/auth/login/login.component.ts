// src/app/features/auth/login/login.component.ts

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AuthService, LoginRequest } from '../../../core/services/auth.service';
import { RolService, Rol } from '../../../core/services/rol.service';

@Component({
  selector: 'app-login',
  standalone: true,              // marcamos standalone
  imports: [
    CommonModule,                 // para poder usar *ngIf
    FormsModule                   // para poder usar [(ngModel)]
  ],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  nombre = '';
  contrasena = '';
  codigoRol = '';
  roles: Rol[] = [];
  cargando = false;
  errorMsg = '';

  constructor(
    private authService: AuthService,
    private rolService: RolService,
    private router: Router
  ) {}

  ngOnInit() {
    this.rolService.getRoles().subscribe({
      next: (roles) => (this.roles = roles),
      error: (err) =>
        alert('Error al cargar roles: ' + (err.error?.message || err.message))
    });
  }

  onLogin() {
    this.errorMsg = '';

    if (!this.nombre || !this.contrasena || !this.codigoRol) {
      this.errorMsg = 'Todos los campos son obligatorios';
      return;
    }

    this.cargando = true;
    const credentials: LoginRequest = {
      nombre: this.nombre,
      contrasena: this.contrasena,
      codigoRol: this.codigoRol
    };

    this.authService.login(credentials).subscribe({
      next: (uuid: string) => {
        this.authService.storeUserId(uuid);
        this.router.navigate(['/categorias']);
      },
      error: (err) => {
        this.cargando = false;
        this.errorMsg = 'Credenciales incorrectas.';
      }
    });
  }
}
