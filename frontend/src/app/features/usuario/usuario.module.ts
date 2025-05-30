import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { UsuarioRoutingModule } from './usuario-routing.module';
import { UsuarioCrudComponent } from './usuario-crud/usuario-crud.component';
import { MaterialModule } from '../../material.module';
import { FormsModule } from '@angular/forms';

@NgModule({
  declarations: [UsuarioCrudComponent],
  imports: [CommonModule, UsuarioRoutingModule, MaterialModule, FormsModule]
})
export class UsuarioModule {}
