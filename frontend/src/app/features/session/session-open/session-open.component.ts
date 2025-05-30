import { Component } from '@angular/core';
import { SesionTrabajoService, SesionTrabajo } from '../../../core/services/sesion-trabajo.service';

@Component({
  selector: 'app-session-open',
  standalone: false,
  templateUrl: './session-open.component.html',
  styleUrls: ['./session-open.component.css']
})
export class SessionOpenComponent {
  usuarioId: string = '';
  fechaApertura: string = '';

  constructor(private sesionTrabajoService: SesionTrabajoService) {}

  abrirSesion() {
    if (!this.usuarioId) {
      alert('Selecciona un usuario');
      return;
    }
    const sesion: SesionTrabajo = {
      idUsuario: this.usuarioId,
      baseCaja: '0',
      fechaApertura: new Date().toISOString()
    };
    this.sesionTrabajoService.abrirSesion(sesion).subscribe({
      next: () => {
        alert('Sesión abierta exitosamente');
      },
      error: (err: any) => alert('Error al abrir sesión: ' + (err.error?.message || err.message))
    });
  }
}
