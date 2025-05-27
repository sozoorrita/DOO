// src/app/features/ventas/pages/venta-list/venta-list.component.ts
import { Component, OnInit } from '@angular/core';
import { VentaFacade } from '../../../../core/facades/venta.facade';
import { Venta } from '../../../../core/models/venta.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-venta-list',
  standalone: false,
  templateUrl: './venta-list.component.html',
  styleUrls: ['./venta-list.component.css']
})
export class VentaListComponent implements OnInit {
  ventas: Venta[] = [];
  displayedColumns = ['id','usuarioId','fecha','total','acciones'];

  constructor(private facade: VentaFacade, private router: Router) {}

  ngOnInit(): void {
    this.load();
  }

  load(): void {
    this.facade.getAll().subscribe(data => this.ventas = data);
  }

  onNuevo(): void {
    this.router.navigate(['ventas','nuevo']);
  }

  onEditar(id: string): void {
    this.router.navigate(['ventas', id, 'editar']);
  }

  onEliminar(id: string): void {
    this.facade.delete(id).subscribe(() => this.load());
  }

  onPOS(): void {
    this.router.navigate(['ventas','pos']);
  }
}
