import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { InformeCajaService, InformeCaja } from '../../../core/services/informe-caja.service';

@Component({
  selector: 'app-informe-caja-detail',
  standalone: false,
  templateUrl: './informe-caja-detail.component.html',
  styleUrls: ['./informe-caja-detail.component.css']
})
export class InformeCajaDetailComponent implements OnInit {
  informe: InformeCaja | null = null;
  codigoInforme: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private informeCajaService: InformeCajaService
  ) {}

  ngOnInit() {
    this.codigoInforme = this.route.snapshot.paramMap.get('codigo');
    if (this.codigoInforme) {
      this.informeCajaService.getInformePorId(this.codigoInforme).subscribe({
        next: informe => this.informe = informe,
        error: err => alert('Error al cargar el informe: ' + (err.error?.message || err.message))
      });
    }
  }

  consolidarInforme() {
    if (!this.informe) return;
    this.informeCajaService.consolidarInforme(this.informe).subscribe({
      next: () => {
        alert('Informe consolidado correctamente');
        this.router.navigate(['/informe-caja']);
      },
      error: err => alert('Error al consolidar informe: ' + (err.error?.message || err.message))
    });
  }
}
