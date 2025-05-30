import { Component, OnInit } from '@angular/core';
import { MesaService, Mesa } from '../../../core/services/mesa.service';
import { EstadoMesaService, EstadoMesa } from '../../../core/services/estado-mesa.service';

@Component({
  selector: 'app-mesa-list',
  standalone: false,
  templateUrl: './mesa-list.component.html',
  styleUrls: ['./mesa-list.component.css']
})
export class MesaListComponent implements OnInit {
  mesas: Mesa[] = [];
  estados: EstadoMesa[] = [];

  // Esto es para el formulario de crear/editar
  form: Mesa = { nombre: '', codigoEstadoMesa: '' };
  editando: Mesa | null = null;

  constructor(
    private mesaService: MesaService,
    private estadoMesaService: EstadoMesaService
  ) {}

  ngOnInit() {
    this.cargarEstados();
    this.cargarMesas();
  }

  private cargarEstados() {
    this.estadoMesaService.getEstadoMesas().subscribe({
      next: estados => this.estados = estados,
      error: err => alert('Error al cargar estados de mesa: ' + (err.error?.message || err.message))
    });
  }

  private cargarMesas() {
    this.mesaService.getMesas().subscribe({
      next: mesas => this.mesas = mesas,
      error: err => alert('Error al cargar mesas: ' + (err.error?.message || err.message))
    });
  }

  guardarMesa() {
    // Validación simple
    if (!this.form.nombre || !this.form.codigoEstadoMesa) {
      alert('Todos los campos son obligatorios');
      return;
    }

    if (this.editando) {
      // Si estamos en modo edición, llamamos a modificar
      this.mesaService.modificar(this.editando.codigo!, this.form).subscribe({
        next: () => {
          this.cancelar();
          this.cargarMesas();
        },
        error: err => alert('Error al actualizar mesa: ' + (err.error?.message || err.message))
      });
    } else {
      // Si no estamos editando, creamos nueva mesa
      this.mesaService.registrar(this.form).subscribe({
        next: () => {
          // Limpiamos el formulario
          this.form = { nombre: '', codigoEstadoMesa: '' };
          this.cargarMesas();
        },
        error: err => alert('Error al crear mesa: ' + (err.error?.message || err.message))
      });
    }
  }

  editarMesa(mesa: Mesa) {
    this.editando = mesa;
    // Clonamos el objeto para llenar el formulario
    this.form = { nombre: mesa.nombre, codigoEstadoMesa: mesa.codigoEstadoMesa || '' };
  }

  eliminarMesa(codigo: string) {
    if (confirm('¿Seguro que deseas eliminar esta mesa?')) {
      this.mesaService.eliminar(codigo).subscribe({
        next: () => this.cargarMesas(),
        error: err => alert('Error al eliminar mesa: ' + (err.error?.message || err.message))
      });
    }
  }

  cancelar() {
    this.editando = null;
    this.form = { nombre: '', codigoEstadoMesa: '' };
  }
}
