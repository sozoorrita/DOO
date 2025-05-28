import { Component, OnInit } from '@angular/core';
import { SesionTrabajoService } from '../../core/facades/sesion-trabajo.service';
import { SesionTrabajo } from '../../core/models/sesion-trabajo.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-sesion-trabajo',
    standalone: false,
  templateUrl: './sesion-trabajo.component.html',
  styleUrls: ['./sesion-trabajo.component.css']
})
export class SesionTrabajoComponent implements OnInit {
  lista: SesionTrabajo[] = [];
  nuevo: SesionTrabajo = {
    codigo: '',
    fechaInicio: '',
    fechaFin: '',
    codigoUsuario: ''
  };
  cargando = false;
  error = '';

  constructor(private svc: SesionTrabajoService) {}

  ngOnInit() {
    this.cargarSesiones();
  }

  cargarSesiones() {
    this.cargando = true;
    this.error = '';
    this.svc.getAll().subscribe({
      next: datos => {
        this.lista = datos;
        this.cargando = false;
      },
      error: err => {
        this.error = 'Error al cargar sesiones de trabajo';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  guardar() {
    this.cargando = true;
    this.error = '';
    const peticion: Observable<any> = this.nuevo.codigo
      ? this.svc.update(this.nuevo.codigo, this.nuevo)
      : this.svc.create(this.nuevo);

    peticion.subscribe({
      next: () => {
        this.cargarSesiones();
        this.nuevo = { codigo: '', fechaInicio: '', fechaFin: '', codigoUsuario: '' };
      },
      error: err => {
        this.error = 'Error al guardar la sesión de trabajo';
        console.error(err);
        this.cargando = false;
      }
    });
  }

  eliminar(codigo: string) {
    if (!confirm('¿Seguro que deseas eliminar esta sesión de trabajo?')) {
      return;
    }
    this.cargando = true;
    this.error = '';
    this.svc.delete(codigo).subscribe({
      next: () => this.cargarSesiones(),
      error: err => {
        this.error = 'Error al eliminar la sesión de trabajo';
        console.error(err);
        this.cargando = false;
      }
    });
  }
}
