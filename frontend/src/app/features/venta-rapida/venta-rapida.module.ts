import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { VentaRapidaComponent } from './venta-rapida/venta-rapida.component';
import { VentaRapidaRoutingModule } from './venta-rapida-routing.module';

@NgModule({
  declarations: [
    VentaRapidaComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    VentaRapidaRoutingModule
  ],
  exports: [
    VentaRapidaComponent
  ]
})
export class VentaRapidaModule { }
