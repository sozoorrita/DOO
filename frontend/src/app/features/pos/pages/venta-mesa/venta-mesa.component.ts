import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-venta-mesa',
    standalone: false,
  templateUrl: './venta-mesa.component.html'
})
export class VentaMesaComponent implements OnInit {
  mesaId!: string;
  constructor(private route: ActivatedRoute) {}
  ngOnInit(): void {
    this.mesaId = this.route.snapshot.paramMap.get('mesaId')!;
  }
}
