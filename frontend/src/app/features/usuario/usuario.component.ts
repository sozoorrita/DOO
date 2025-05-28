import { Component, OnInit } from '@angular/core';
import { UsuarioService } from '../../core/facades/usuario.service';
import { Usuario } from '../../core/models/usuario.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-usuario',
    standalone: false,
  templateUrl: './usuario.component.html',
  styleUrls: ['./usuario.component.css']
})
export class UsuarioComponent implements OnInit {
  lista: Usuario[] = [];
  nuevo: Usuario = { codigo: '', nombre: '', correo: '', rolCodigo: '' };
  cargando = false;
  error = '';

  constructor(private svc: UsuarioService) {}

  ngOnInit() {
    this.cargarUsuarios();
  }

  cargarUsuarios() {
    this.cargando = true;
    this.error = '';
    this.svc.getAll().subscribe({
      next: data => {
        this.lista = data;
        this.cargando = false;
      },
      error: err => {
        this.error = 'Error al cargar usuarios';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  guardar() {
    this.cargando = true;
    this.error = '';

    const peticion: Observable<Usuario | void> = this.nuevo.codigo
      ? this.svc.update(this.nuevo.codigo, this.nuevo)
      : this.svc.create(this.nuevo);

    peticion.subscribe({
      next: () => {
        this.cargarUsuarios();
        this.nuevo = { codigo: '', nombre: '', correo: '', rolCodigo: '' };
      },
      error: err => {
        this.error = 'Error al guardar el usuario';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  eliminar(codigo: string) {
    if (!confirm('¿Seguro que deseas eliminar este usuario?')) {
      return;
    }
    this.cargando = true;
    this.error = '';
    this.svc.delete(codigo).subscribe({
      next: () => this.cargarUsuarios(),
      error: err => {
        this.error = 'Error al eliminar el usuario';
        console.error(err);
        this.cargando = false;
      }
    });
  }
}
