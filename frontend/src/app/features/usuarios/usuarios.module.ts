import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { UsuariosRoutingModule } from './usuarios-routing.module';
import { UsuarioListComponent } from './pages/usuario-list/usuario-list.component';
import { UsuarioFormComponent } from './pages/usuario-form/usuario-form.component';
import { MaterialModule } from '../../material.module';

@NgModule({
  declarations: [UsuarioListComponent, UsuarioFormComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    UsuariosRoutingModule,
    MaterialModule
  ]
})
export class UsuariosModule {}
