import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UsuarioService, Usuario } from '../../../core/services/usuario.service';
import { RolService, Rol } from '../../../core/services/rol.service';

@Component({
  selector: 'app-usuario-crud',
  standalone: false,
  templateUrl: './usuario-crud.component.html',
  styleUrls: ['./usuario-crud.component.css']
})
export class UsuarioCrudComponent implements OnInit {
  usuario: Usuario = { nombre: '', codigoRol: '', contrasena: '' };
  roles: Rol[] = [];
  esEdicion: boolean = false;
  idUsuario?: string;

  constructor(
    private usuarioService: UsuarioService,
    private rolService: RolService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.rolService.getRoles().subscribe({
      next: roles => this.roles = roles,
      error: err => alert('Error al cargar roles: ' + (err.error?.message || err.message))
    });

    this.idUsuario = this.route.snapshot.paramMap.get('id') || undefined;
    if (this.idUsuario) {
      this.esEdicion = true;
      this.usuarioService.getUsuarios().subscribe({
        next: usuarios => {
          const found = usuarios.find(u => u.id === this.idUsuario);
          if (found) {
            this.usuario = { ...found };
          }
        }
      });
    }
  }

  guardarUsuario() {
    if (!this.usuario.nombre || !this.usuario.codigoRol || !this.usuario.contrasena) {
      alert('Todos los campos son obligatorios');
      return;
    }

    if (this.esEdicion && this.idUsuario) {
      this.usuarioService.modificar(this.idUsuario, this.usuario).subscribe({
        next: () => {
          alert('Usuario actualizado');
          this.router.navigate(['/usuarios/list']);
        },
        error: err => alert('Error al actualizar usuario: ' + (err.error?.message || err.message))
      });
    } else {
      this.usuarioService.registrar(this.usuario).subscribe({
        next: () => {
          alert('Usuario creado');
          this.router.navigate(['/usuarios/list']);
        },
        error: err => alert('Error al crear usuario: ' + (err.error?.message || err.message))
      });
    }
  }
}
