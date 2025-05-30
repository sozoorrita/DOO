import { Component, OnInit } from '@angular/core';
import { InformeCajaService, InformeCaja } from '../../../core/services/informe-caja.service';

@Component({
  selector: 'app-informe-caja-list',
  standalone: false,
  templateUrl: './informe-caja-list.component.html',
  styleUrls: ['./informe-caja-list.component.css']
})
export class InformeCajaListComponent implements OnInit {
  informes: InformeCaja[] = [];

  constructor(private informeCajaService: InformeCajaService) {}

  ngOnInit() {
    this.cargarInformes();
  }

  cargarInformes() {
    this.informeCajaService.getInformes().subscribe({
      next: informes => this.informes = informes,
      error: err => alert('Error al cargar informes: ' + (err.error?.message || err.message))
    });
  }
}
