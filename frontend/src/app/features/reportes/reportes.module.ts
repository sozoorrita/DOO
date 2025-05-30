import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReportesRoutingModule } from './reportes-routing.module';
import { InformeCajaComponent } from './informe-caja/informe-caja.component';
import { MaterialModule } from '../../material.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [InformeCajaComponent],
  imports: [CommonModule, ReportesRoutingModule, MaterialModule, FormsModule]
})
export class ReportesModule {}