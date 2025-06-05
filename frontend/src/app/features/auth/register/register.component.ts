// src/app/features/auth/register/register.component.ts

import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UsuarioService } from '../../../core/services/usuario.service'; // o donde manejes el registro
import { RolService, Rol } from '../../../core/services/rol.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule
  ],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  nombre = '';
  contrasena = '';
  confirmarContrasena = '';
  codigoRol = '';
  roles: Rol[] = [];
  errorMsg = '';

  constructor(
    private usuarioService: UsuarioService,
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

  onRegister() {
    this.errorMsg = '';

    if (!this.nombre || !this.contrasena || !this.confirmarContrasena || !this.codigoRol) {
      this.errorMsg = 'Todos los campos son obligatorios';
      return;
    }

    if (this.contrasena !== this.confirmarContrasena) {
      this.errorMsg = 'Las contraseñas no coinciden';
      return;
    }


  }

  goToLogin() {
    this.router.navigate(['/auth/login']);
  }
}
