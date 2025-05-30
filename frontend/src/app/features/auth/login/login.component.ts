import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService, Usuario } from '../../../core/services/usuario.service';
import { RolService, Rol } from '../../../core/services/rol.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  nombre = '';
  contrasena = '';
  codigoRol = '';
  tipoUsuario = 'mesero'; // o 'administrador' según tu flujo
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

  onLogin() {
    if (!this.nombre || !this.contrasena || !this.codigoRol) {
      alert('Todos los campos son obligatorios');
      return;
    }

    const usuario: Usuario = {
      nombre: this.nombre,
      contrasena: this.contrasena,
      codigoRol: this.codigoRol
    };

    this.usuarioService.iniciarSesion(usuario, this.tipoUsuario).subscribe({
      next: () => {
        localStorage.setItem('usuario', JSON.stringify(usuario));
        alert('Bienvenido');
        this.router.navigate(['/dashboard']);
      },
      error: err => {
        alert('Error al iniciar sesión: ' + (err.error?.message || err.message));
      }
    });
  }

  goToRegister() {
    this.router.navigate(['/register']);
  }
}
