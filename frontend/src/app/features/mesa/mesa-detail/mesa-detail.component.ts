import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MesaService, Mesa } from '../../../core/services/mesa.service';

@Component({
  selector: 'app-mesa-detail',
  standalone: false,
  templateUrl: './mesa-detail.component.html',
  styleUrls: ['./mesa-detail.component.css']
})
export class MesaDetailComponent implements OnInit {
  mesa: Mesa | null = null;
  codigoMesa: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private mesaService: MesaService
  ) {}

  ngOnInit() {
    this.codigoMesa = this.route.snapshot.paramMap.get('codigo');
    if (this.codigoMesa) {
      this.mesaService.getMesaPorId(this.codigoMesa).subscribe({
        next: mesa => this.mesa = mesa,
        error: err => alert('Error al cargar la mesa: ' + (err.error?.message || err.message))
      });
    }
  }

  guardarMesa() {
    if (!this.mesa) return;
    this.mesaService.modificar(this.mesa.codigo!, this.mesa).subscribe({
      next: () => {
        alert('Mesa actualizada');
        this.router.navigate(['/mesas']);
      },
      error: err => alert('Error al actualizar mesa: ' + (err.error?.message || err.message))
    });
  }
}
