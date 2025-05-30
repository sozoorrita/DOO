import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VentaRapidaRoutingModule } from './venta-rapida-routing.module';
import { VentaRapidaComponent } from './venta-rapida/venta-rapida.component';
import { MaterialModule } from '../../material.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [VentaRapidaComponent],
  imports: [CommonModule, VentaRapidaRoutingModule, MaterialModule, FormsModule]
})
export class VentaRapidaModule {}
