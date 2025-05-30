import { Component, OnInit } from '@angular/core';
import { MesaService, Mesa } from '../../../core/services/mesa.service';

@Component({
  selector: 'app-mesa-list',
  standalone: false,
  templateUrl: './mesa-list.component.html',
  styleUrls: ['./mesa-list.component.css']
})
export class MesaListComponent implements OnInit {
  mesas: Mesa[] = [];
  form: Mesa = { numero: 0, estado: '' };
  editando: Mesa | null = null;

  constructor(private mesaService: MesaService) {}

  ngOnInit() {
    this.cargarMesas();
  }

  cargarMesas() {
    this.mesaService.getMesas().subscribe({
      next: mesas => this.mesas = mesas,
      error: err => alert('Error al cargar mesas: ' + (err.error?.message || err.message))
    });
  }

  guardarMesa() {
    if (this.form.numero == null || this.form.estado == '') {
      alert('Todos los campos son obligatorios');
      return;
    }

    if (this.editando) {
      this.mesaService.modificar(this.editando.codigo!, this.form).subscribe({
        next: () => {
          this.cancelar();
          this.cargarMesas();
        },
        error: err => alert('Error al editar mesa: ' + (err.error?.message || err.message))
      });
    } else {
      this.mesaService.registrar(this.form).subscribe({
        next: () => {
          this.form = { numero: 0, estado: '' };
          this.cargarMesas();
        },
        error: err => alert('Error al crear mesa: ' + (err.error?.message || err.message))
      });
    }
  }

  editarMesa(mesa: Mesa) {
    this.editando = mesa;
    this.form = { ...mesa };
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
    this.form = { numero: 0, estado: '' };
  }
}
