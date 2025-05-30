import { Component, OnInit } from '@angular/core';
import { InventarioService, Inventario } from '../../../core/services/inventario.service';

@Component({
  selector: 'app-inventory-list',
  standalone: false,
  templateUrl: './inventory-list.component.html',
  styleUrls: ['./inventory-list.component.css']
})
export class InventoryListComponent implements OnInit {
  inventarios: Inventario[] = [];
  // Para crear/editar inventarios:
  form: Inventario = { nombre: '', cantidad: 0 };
  editando: Inventario | null = null;

  constructor(private inventarioService: InventarioService) {}

  ngOnInit() {
    this.cargarInventarios();
  }

  cargarInventarios() {
    this.inventarioService.getInventarios().subscribe({
      next: inventarios => this.inventarios = inventarios,
      error: err => alert('Error al cargar inventarios: ' + (err.error?.message || err.message))
    });
  }

  guardarInventario() {
    if (!this.form.nombre || this.form.cantidad == null) {
      alert('Todos los campos son obligatorios');
      return;
    }

    if (this.editando) {
      this.inventarioService.modificar(this.editando.codigo!, this.form).subscribe({
        next: () => {
          this.cancelar();
          this.cargarInventarios();
        },
        error: err => alert('Error al editar inventario: ' + (err.error?.message || err.message))
      });
    } else {
      this.inventarioService.registrar(this.form).subscribe({
        next: () => {
          this.form = { nombre: '', cantidad: 0 };
          this.cargarInventarios();
        },
        error: err => alert('Error al crear inventario: ' + (err.error?.message || err.message))
      });
    }
  }

  editarInventario(inventario: Inventario) {
    this.editando = inventario;
    this.form = { ...inventario };
  }

  eliminarInventario(codigo: string) {
    if (confirm('¿Seguro que deseas eliminar este inventario?')) {
      this.inventarioService.eliminar(codigo).subscribe({
        next: () => this.cargarInventarios(),
        error: err => alert('Error al eliminar inventario: ' + (err.error?.message || err.message))
      });
    }
  }

  cancelar() {
    this.editando = null;
    this.form = { nombre: '', cantidad: 0 };
  }
}
