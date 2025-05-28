import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { VentasRoutingModule } from './ventas-routing.module';
import { VentaListComponent } from './pages/venta-list/venta-list.component';
import { VentaFormComponent } from './pages/venta-form/venta-form.component';
import { MaterialModule } from '../../material.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';

@NgModule({
  declarations: [VentaListComponent, VentaFormComponent],
  imports: [
    CommonModule,
    VentasRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule
  ]
})
export class VentasModule {}
