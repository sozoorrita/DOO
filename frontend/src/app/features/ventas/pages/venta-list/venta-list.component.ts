import { Component, OnInit } from '@angular/core';
import { VentaFacade } from '../../../../core/facades/venta.service';
import { Venta } from '../../../../core/models/venta.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-venta-list',
  standalone: false,
  templateUrl: './venta-list.component.html'
})
export class VentaListComponent implements OnInit {
  ventas: Venta[] = [];
  displayedColumns = ['id','usuarioId','fecha','total','acciones'];
  constructor(private facade: VentaFacade, private router: Router) {}
  ngOnInit(): void { this.facade.getAll().subscribe(v => this.ventas = v); }
  onNuevo(): void { this.router.navigate(['ventas','nuevo']); }
  onEditar(id: string): void { this.router.navigate(['ventas', id, 'editar']); }
  onEliminar(id: string): void { this.facade.delete(id).subscribe(() => this.ngOnInit()); }
}
