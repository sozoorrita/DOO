import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VentasRoutingModule } from './ventas-routing.module';
import { VentaListComponent } from './pages/venta-list/venta-list.component';
import { VentaFormComponent } from './pages/venta-form/venta-form.component';
import { PosVentaComponent } from './pages/pos-venta/pos-venta.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';

@NgModule({
  declarations: [VentaListComponent, VentaFormComponent, PosVentaComponent],
  imports: [
    CommonModule,
    VentasRoutingModule,
    ReactiveFormsModule,   
    FormsModule,           
    MaterialModule         
  ]
})
export class VentasModule {}
