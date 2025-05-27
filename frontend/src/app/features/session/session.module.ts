import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SessionRoutingModule } from './session-routing.module';
import { AbrirSesionComponent } from './pages/abrir-sesion/abrir-sesion.component';
import { InformeCajaComponent } from './pages/informe-caja/informe-caja.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { MaterialModule } from '../../material.module';

@NgModule({
  declarations: [AbrirSesionComponent, InformeCajaComponent],
  imports: [
    CommonModule,
    SessionRoutingModule,
    ReactiveFormsModule,
    FormsModule,
    MaterialModule
  ]
})
export class SessionModule {}
