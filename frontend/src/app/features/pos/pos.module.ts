import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PosRoutingModule } from './pos-routing.module';
import { MesasComponent } from './pages/mesas/mesas.component';
import { VentaNormalComponent } from './pages/venta-normal/venta-normal.component';
import { VentaMesaComponent } from './pages/venta-mesa/venta-mesa.component';
import { VentaFormComponent } from '../ventas/pages/venta-form/venta-form.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';

@NgModule({
  declarations: [
    MesasComponent,
    VentaNormalComponent,
    VentaMesaComponent,
    VentaFormComponent
  ],
  imports: [
    CommonModule,
    PosRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule
  ]
})
export class PosModule {}
