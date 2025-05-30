import { Component, OnInit } from '@angular/core';
import { UsuarioService, Usuario } from '../../../core/services/usuario.service';

@Component({
  selector: 'app-usuario-list',
  standalone: false,
  templateUrl: './usuario-list.component.html',
  styleUrls: ['./usuario-list.component.css']
})
export class UsuarioListComponent implements OnInit {
  usuarios: Usuario[] = [];

  constructor(private usuarioService: UsuarioService) {}

  ngOnInit() {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.usuarioService.getUsuarios().subscribe({
      next: usuarios => this.usuarios = usuarios,
      error: err => alert('Error al cargar usuarios: ' + (err.error?.message || err.message))
    });
  }
}
