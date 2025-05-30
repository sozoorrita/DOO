import { Component, OnInit } from '@angular/core';
import { InventarioService, Inventario } from '../../../core/services/inventario.service';
import { ProductoService, Producto } from '../../../core/services/producto.service';
import { IndicadorInventarioService, IndicadorInventario } from '../../../core/services/indicador-inventario.service';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-inventory-list',
  templateUrl: './inventory-list.component.html',
  imports: [
    FormsModule
  ],
  styleUrls: ['./inventory-list.component.css']
})
export class InventoryListComponent implements OnInit {
  inventarios: Inventario[] = [];
  productos: Producto[] = [];
  indicadores: IndicadorInventario[] = [];

  // Formulario básico para crear/editar
  form = {
    producto: '',        // UUID del producto
    cantidad: 0,
    codigoIndicador: ''  // UUID del indicador de inventario
  };
  editando: Inventario | null = null;

  constructor(
    private inventarioService: InventarioService,
    private productoService: ProductoService,
    private indicadorInventarioService: IndicadorInventarioService
  ) {}

  ngOnInit() {
    this.cargarInventarios();
    this.productoService.getProductos().subscribe(data => this.productos = data);
    this.indicadorInventarioService.getIndicadorInventarios().subscribe(data => this.indicadores = data);
  }

  cargarInventarios() {
    this.inventarioService.getInventarios().subscribe({
      next: inventarios => this.inventarios = inventarios,
      error: err => alert('Error al cargar inventarios: ' + (err.error?.message || err.message))
    });
  }

  guardarInventario() {
    if (!this.form.producto || this.form.cantidad == null || !this.form.codigoIndicador) {
      alert('Todos los campos son obligatorios');
      return;
    }

    const inventario: Inventario = {
      producto: this.form.producto,                // Solo UUID
      cantidad: this.form.cantidad,
      codigoIndicador: this.form.codigoIndicador   // Solo UUID
    };

    if (this.editando) {
      this.inventarioService.modificar(this.editando.codigo!, inventario).subscribe({
        next: () => {
          this.cancelar();
          this.cargarInventarios();
        },
        error: err => alert('Error al editar inventario: ' + (err.error?.message || err.message))
      });
    } else {
      this.inventarioService.registrar(inventario).subscribe({
        next: () => {
          this.resetForm();
          this.cargarInventarios();
        },
        error: err => alert('Error al crear inventario: ' + (err.error?.message || err.message))
      });
    }
  }

  editarInventario(inventario: Inventario) {
    this.editando = inventario;
    this.form = {
      producto: inventario.producto,            // Solo UUID
      cantidad: inventario.cantidad,
      codigoIndicador: inventario.codigoIndicador // Solo UUID
    };
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
    this.resetForm();
  }

  resetForm() {
    this.form = {
      producto: '',
      cantidad: 0,
      codigoIndicador: ''
    };
  }
}
