import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MesaRoutingModule } from './mesa-routing.module';
import { MesaListComponent } from './mesa-list/mesa-list.component';
import { MesaDetailComponent } from './mesa-detail/mesa-detail.component';
import { MaterialModule } from '../../material.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [MesaListComponent, MesaDetailComponent],
  imports: [CommonModule, MesaRoutingModule, MaterialModule, FormsModule]
})
export class MesaModule {}
