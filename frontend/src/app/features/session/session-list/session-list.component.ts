import { Component, OnInit } from '@angular/core';
import { SesionTrabajoService, SesionTrabajo } from '../../../core/services/sesion-trabajo.service';

@Component({
  selector: 'app-session-list',
  standalone: false,
  templateUrl: './session-list.component.html',
  styleUrls: ['./session-list.component.css']
})
export class SessionListComponent implements OnInit {
  sesiones: SesionTrabajo[] = [];

  constructor(private sesionTrabajoService: SesionTrabajoService) {}

  ngOnInit() {
    this.cargarSesiones();
  }

  cargarSesiones() {
    this.sesionTrabajoService.getSesiones().subscribe({
      next: sesiones => this.sesiones = sesiones,
      error: err => alert('Error al cargar sesiones: ' + (err.error?.message || err.message))
    });
  }
}
