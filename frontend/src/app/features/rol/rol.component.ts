import { Component, OnInit } from '@angular/core';
import { RolService } from '../../core/facades/rol.service';
import { Rol } from '../../core/models/rol.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-rol',
    standalone: false,
  templateUrl: './rol.component.html',
  styleUrls: ['./rol.component.css']
})
export class RolComponent implements OnInit {
  lista: Rol[] = [];
  nuevo: Rol = { codigo: '', nombre: '' };
  cargando = false;
  error = '';

  constructor(private svc: RolService) {}

  ngOnInit() {
    this.cargarRoles();
  }

  cargarRoles() {
    this.cargando = true;
    this.error = '';
    this.svc.getAll().subscribe({
      next: datos => {
        this.lista = datos;
        this.cargando = false;
      },
      error: err => {
        this.error = 'Error al cargar roles';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  guardar() {
    this.cargando = true;
    this.error = '';
    const peticion: Observable<Rol | void> = this.nuevo.codigo
      ? this.svc.update(this.nuevo.codigo, this.nuevo)
      : this.svc.create(this.nuevo);

    peticion.subscribe({
      next: () => {
        this.cargarRoles();
        this.nuevo = { codigo: '', nombre: '' };
      },
      error: err => {
        this.error = 'Error al guardar el rol';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  eliminar(codigo: string) {
    if (!confirm('¿Seguro que deseas eliminar este rol?')) {
      return;
    }
    this.cargando = true;
    this.error = '';
    this.svc.delete(codigo).subscribe({
      next: () => this.cargarRoles(),
      error: err => {
        this.error = 'Error al eliminar el rol';
        console.error(err);
        this.cargando = false;
      }
    });
  }
}
