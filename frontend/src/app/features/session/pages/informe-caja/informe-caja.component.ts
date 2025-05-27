import { Component, OnInit } from '@angular/core';
import { SessionFacade } from '../../../../core/facades/session.facade';

@Component({
  selector: 'app-informe-caja',
  templateUrl: './informe-caja.component.html',
  styleUrls: ['./informe-caja.component.css']
})
export class InformeCajaComponent implements OnInit {
  totalVentas = 0;
  constructor(private facade: SessionFacade) {}
  ngOnInit(): void {
    this.facade.getReport().subscribe(r => {
      this.totalVentas = r.total;
    });
  }
  cerrarSesion(): void {
    this.facade.close().subscribe(() => location.reload());
  }
}
