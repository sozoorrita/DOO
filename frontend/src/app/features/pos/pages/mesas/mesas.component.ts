import { Component, OnInit } from '@angular/core';
import { Mesa } from '../../../../core/models/mesa.model';
import { PosFacade } from '../../../../core/facades/pos.facade';
import { Router } from '@angular/router';

@Component({
  selector: 'app-mesas',
  standalone: false,
  templateUrl: './mesas.component.html',
  styleUrls: ['./mesas.component.css']
})
export class MesasComponent implements OnInit {
  mesas: Mesa[] = [];
  constructor(private facade: PosFacade, private router: Router) {}
  ngOnInit(): void {
    this.facade.getMesas().subscribe(data => this.mesas = data);
  }
  irVentaMesa(id: string): void {
    this.router.navigate(['/pos/venta-mesa', id]);
  }
}
