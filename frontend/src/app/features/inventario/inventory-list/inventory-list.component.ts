import { Component, OnInit } from '@angular/core';
import { InventarioService, Inventario } from '../../../core/services/inventario.service';
import { ProductoService, Producto } from '../../../core/services/producto.service';
import { IndicadorInventarioService, IndicadorInventario } from '../../../core/services/indicador-inventario.service';

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

  // El formulario refleja los campos del DTO Inventario
  form: { codigoProducto: string; cantidad: number; codigoIndicador: string } = {
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
    this.productoService.getProductos().subscribe(p => this.productos = p);
    this.indicadorService.getIndicadores().subscribe(i => this.indicadores = i);
    this.cargarInventarios();
  }

  cargarInventarios() {
    this.inventarioService.getInventarios().subscribe({
      next: data => this.inventarios = data,
      error: err => alert('Error al cargar inventarios: ' + err.message)
    });
  }

  guardar() {
    if (!this.form.codigoProducto || this.form.cantidad <= 0 || !this.form.codigoIndicador) {
      alert('Todos los campos son obligatorios y cantidad > 0');
      return;
    }
    if (this.editando) {
      this.inventarioService.modificar(this.editando.codigo!, this.form as Inventario).subscribe({
        next: () => { this.cancelar(); this.cargarInventarios(); },
        error: err => alert('Error al actualizar: ' + err.message)
      });
    } else {
      this.inventarioService.registrar(this.form as Inventario).subscribe({
        next: () => { this.cancelar(); this.cargarInventarios(); },
        error: err => alert('Error al crear: ' + err.message)
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
    if (confirm('¿Eliminar este inventario?')) {
      this.inventarioService.eliminar(codigo).subscribe({
        next: () => this.cargarInventarios(),
        error: err => alert('Error al eliminar: ' + err.message)
      });
    }
  }

  cancelar() {
    this.editando = null;
    this.form = { codigoProducto: '', cantidad: 0, codigoIndicador: '' };
  }

  nombreProducto(cod: string) {
    const p = this.productos.find(x => x.codigo === cod);
    return p ? p.nombre : '-';
  }

  nombreIndicador(cod: string) {
    const i = this.indicadores.find(x => x.codigo === cod);
    return i ? i.nombre : '-';
  }
}
