import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService, Usuario } from '../../../core/services/usuario.service';
import { RolService, Rol } from '../../../core/services/rol.service';

@Component({
  selector: 'app-register',
    standalone: false,
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {
  nombre = '';
  contrasena = '';
  confirmarContrasena = '';
  codigoRol = '';
  roles: Rol[] = [];

  constructor(
    private usuarioService: UsuarioService,
    private rolService: RolService,
    private router: Router
  ) {}

  ngOnInit() {
    this.rolService.getRoles().subscribe({
      next: roles => this.roles = roles,
      error: err => alert('Error al cargar roles: ' + (err.error?.message || err.message))
    });
  }

  onRegister() {
    if (!this.nombre || !this.contrasena || !this.codigoRol) {
      alert('Todos los campos son obligatorios');
      return;
    }
    if (this.contrasena !== this.confirmarContrasena) {
      alert('Las contraseñas no coinciden');
      return;
    }

    const usuario: Usuario = {
      nombre: this.nombre,
      contrasena: this.contrasena,
      codigoRol: this.codigoRol
    };

    this.usuarioService.registrar(usuario).subscribe({
      next: () => {
        alert('Registro exitoso, ahora inicia sesión.');
        this.router.navigate(['/login']);
      },
      error: err => {
        alert('Error al registrar: ' + (err.error?.message || err.message));
      }
    });
  }

  goToLogin() {
    this.router.navigate(['/login']);
  }
}
