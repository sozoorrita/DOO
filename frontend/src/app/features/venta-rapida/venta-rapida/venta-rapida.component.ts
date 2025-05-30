import { Component, OnInit } from '@angular/core';
import { VentaService, Venta } from '../../../core/services/venta.service';
import { ProductoService, Producto } from '../../../core/services/producto.service';
import { MesaService, Mesa } from '../../../core/services/mesa.service';
import { UsuarioService, Usuario } from '../../../core/services/usuario.service';

@Component({
  selector: 'app-venta-rapida',
  standalone: false,
  templateUrl: './venta-rapida.component.html',
  styleUrls: ['./venta-rapida.component.css']
})
export class VentaRapidaComponent implements OnInit {
  productos: Producto[] = [];
  mesas: Mesa[] = [];
  usuarios: Usuario[] = [];
  venta: Venta = {
    producto: null,
    mesa: null,
    usuario: null,
    cantidad: 1,
    // otros campos según tu backend
  };

  constructor(
    private ventaService: VentaService,
    private productoService: ProductoService,
    private mesaService: MesaService,
    private usuarioService: UsuarioService
  ) {}

  ngOnInit() {
    this.productoService.getProductos().subscribe({
      next: productos => this.productos = productos
    });
    this.mesaService.getMesas().subscribe({
      next: mesas => this.mesas = mesas
    });
    this.usuarioService.getUsuarios().subscribe({
      next: usuarios => this.usuarios = usuarios
    });
  }

  realizarVenta() {
    if (!this.venta.producto || !this.venta.mesa || !this.venta.usuario || !this.venta.cantidad) {
      alert('Todos los campos son obligatorios');
      return;
    }
    this.ventaService.registrarVenta(this.venta).subscribe({
      next: () => {
        alert('Venta realizada exitosamente');
        this.venta = { producto: null, mesa: null, usuario: null, cantidad: 1 };
      },
      error: err => alert('Error al registrar la venta: ' + (err.error?.message || err.message))
    });
  }
}
