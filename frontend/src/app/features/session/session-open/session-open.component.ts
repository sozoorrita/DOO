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
  estado: string = 'Abierta';
  fechaApertura: string = '';
  // Si quieres más campos, agrégalos aquí

  constructor(private sesionTrabajoService: SesionTrabajoService) {}

  abrirSesion() {
    if (!this.usuarioId) {
      alert('Selecciona un usuario');
      return;
    }
    const sesion: SesionTrabajo = {
      usuario: { id: this.usuarioId },
      fechaApertura: new Date().toISOString(),
      estado: this.estado
    };
    this.sesionTrabajoService.abrirSesion(sesion).subscribe({
      next: () => {
        alert('Sesión abierta exitosamente');
        // Opcional: recargar lista o navegar
      },
      error: err => alert('Error al abrir sesión: ' + (err.error?.message || err.message))
    });
  }
}
