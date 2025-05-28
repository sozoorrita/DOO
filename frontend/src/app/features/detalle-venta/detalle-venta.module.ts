import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';

import { DetalleVentaRoutingModule } from './detalle-venta-routing.module';
import { DetalleVentaListComponent } from './pages/detalle-venta-list/detalle-venta-list.component';
import { DetalleVentaFormComponent } from './pages/detalle-venta-form/detalle-venta-form.component';

@NgModule({
  declarations: [
    DetalleVentaListComponent,
    DetalleVentaFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    DetalleVentaRoutingModule
  ]
})
export class DetalleVentaModule { }
