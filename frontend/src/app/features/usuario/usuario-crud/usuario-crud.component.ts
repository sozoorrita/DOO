import { Component, OnInit } from '@angular/core';
import { UsuarioService, Usuario } from '../../../core/services/usuario.service';
import { RolService, Rol } from '../../../core/services/rol.service';

@Component({
  selector: 'app-usuario-crud',
  standalone: false,
  templateUrl: './usuario-crud.component.html',
  styleUrls: ['./usuario-crud.component.css']
})
export class UsuarioCrudComponent implements OnInit {
  usuarios: Usuario[] = [];
  roles: Rol[] = [];
  form: Usuario = { nombre: '', codigoRol: '', contrasena: '' };
  editando: Usuario | null = null;

  constructor(
    private usuarioService: UsuarioService,
    private rolService: RolService
  ) {}

  ngOnInit() {
    this.cargarUsuarios();
    this.rolService.getRoles().subscribe({
      next: roles => this.roles = roles,
      error: err => alert('Error al cargar roles: ' + (err.error?.message || err.message))
    });
  }

  cargarUsuarios() {
    this.usuarioService.getUsuarios().subscribe({
      next: usuarios => this.usuarios = usuarios,
      error: err => alert('Error al cargar usuarios: ' + (err.error?.message || err.message))
    });
  }

  guardarUsuario() {
    if (!this.form.nombre || !this.form.contrasena || !this.form.codigoRol) {
      alert('Todos los campos son obligatorios');
      return;
    }

    if (this.editando) {
      this.usuarioService.modificar(this.editando.id!, this.form).subscribe({
        next: () => {
          this.cancelar();
          this.cargarUsuarios();
        },
        error: err => alert('Error al editar usuario: ' + (err.error?.message || err.message))
      });
    } else {
      this.usuarioService.registrar(this.form).subscribe({
        next: () => {
          this.form = { nombre: '', codigoRol: '', contrasena: '' };
          this.cargarUsuarios();
        },
        error: err => alert('Error al crear usuario: ' + (err.error?.message || err.message))
      });
    }
  }

  editarUsuario(usuario: Usuario) {
    this.editando = usuario;
    this.form = { ...usuario };
  }

  eliminarUsuario(id: string) {
    if (confirm('¿Seguro que deseas eliminar este usuario?')) {
      this.usuarioService.eliminar(id).subscribe({
        next: () => this.cargarUsuarios(),
        error: err => alert('Error al eliminar usuario: ' + (err.error?.message || err.message))
      });
    }
  }

  cancelar() {
    this.editando = null;
    this.form = { nombre: '', codigoRol: '', contrasena: '' };
  }
}
