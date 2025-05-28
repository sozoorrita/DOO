import { Component, OnInit } from '@angular/core';
import { Producto } from '../../../../core/models/producto.model';
import { InventarioFacade } from '../../../../core/facades/inventario.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-productos',
    standalone: false,
  templateUrl: './productos.component.html'
})
export class ProductosComponent implements OnInit {
  productos: Producto[] = [];
  subId!: string;
  constructor(private facade: InventarioFacade, private route: ActivatedRoute) {}
  ngOnInit(): void {
    this.subId = this.route.snapshot.paramMap.get('subId')!;
    this.facade.getProductos(this.subId).subscribe(data => this.productos = data);
  }
}
