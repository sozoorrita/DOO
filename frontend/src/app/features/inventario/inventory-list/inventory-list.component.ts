// src/app/features/inventario/inventory-list/inventory-list.component.ts

import { Component, OnInit } from '@angular/core';
import { InventarioService, Inventario } from '../../../core/services/inventario.service';
import { ProductoService, Producto } from '../../../core/services/producto.service';
import { 
  IndicadorInventarioService, 
  IndicadorInventario 
} from '../../../core/services/indicador-inventario.service';

@Component({
  selector: 'app-inventory-list',
  standalone: false,
  templateUrl: './inventory-list.component.html',
  styleUrls: ['./inventory-list.component.css']
})
export class InventoryListComponent implements OnInit {
  inventarios: Inventario[] = [];
  productos: Producto[] = [];
  indicadores: IndicadorInventario[] = [];

  // Formulario: solo los campos que exige tu interfaz Inventario
  form: {
    codigoProducto: string;
    cantidad: number;
    codigoIndicador: string;
  } = {
    codigoProducto: '',
    cantidad: 0,
    codigoIndicador: ''
  };

  editando: Inventario | null = null;

  constructor(
    private inventarioService: InventarioService,
    private productoService: ProductoService,
    private indicadorService: IndicadorInventarioService
  ) {}

  ngOnInit() {
    // Cargar lista de productos
    this.productoService.getProductos().subscribe(p => (this.productos = p));

    // Cargar lista de indicadores usando el método correcto
    this.indicadorService.getIndicadorInventarios().subscribe(i => (this.indicadores = i));

    // Cargar inventarios existentes
    this.cargarInventarios();
  }

  private cargarInventarios() {
    this.inventarioService.getInventarios().subscribe({
      next: data => (this.inventarios = data),
      error: err => alert('Error al cargar inventarios: ' + err.message)
    });
  }

  guardar() {
    // Validación básica
    if (
      !this.form.codigoProducto ||
      this.form.cantidad <= 0 ||
      !this.form.codigoIndicador
    ) {
      alert('Los campos "Código Producto", "Cantidad (>0)" y "Indicador" son obligatorios.');
      return;
    }

    // Construir el payload según la interfaz
    const payload: Inventario = {
      codigoProducto: this.form.codigoProducto,
      producto: 
        this.productos.find(x => x.codigo === this.form.codigoProducto)?.nombre 
        || '',
      cantidad: this.form.cantidad,
      codigoIndicador: this.form.codigoIndicador
      // No incluimos "codigo" porque el backend lo gestiona
    };

    if (this.editando) {
      // Actualizar inventario existente
      this.inventarioService.modificar(this.editando.codigo!, payload).subscribe({
        next: () => {
          this.cancelar();
          this.cargarInventarios();
        },
        error: err => alert('Error al actualizar inventario: ' + err.message)
      });
    } else {
      // Crear nuevo inventario
      this.inventarioService.registrar(payload).subscribe({
        next: () => {
          this.cancelar();
          this.cargarInventarios();
        },
        error: err => alert('Error al crear inventario: ' + err.message)
      });
    }
  }

  editar(item: Inventario) {
    this.editando = item;
    this.form = {
      codigoProducto: item.codigoProducto,
      cantidad: item.cantidad,
      codigoIndicador: item.codigoIndicador
    };
  }

  eliminar(codigo: string) {
    if (!confirm('¿Desea eliminar este inventario?')) {
      return;
    }
    this.inventarioService.eliminar(codigo).subscribe({
      next: () => this.cargarInventarios(),
      error: err => alert('Error al eliminar inventario: ' + err.message)
    });
  }

  cancelar() {
    this.editando = null;
    this.form = { codigoProducto: '', cantidad: 0, codigoIndicador: '' };
  }
}
