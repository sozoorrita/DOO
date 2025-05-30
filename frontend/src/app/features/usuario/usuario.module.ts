import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { UsuarioListComponent } from './usuario-list/usuario-list.component';
import { UsuarioCrudComponent } from './usuario-crud/usuario-crud.component';
import { UsuarioRoutingModule } from './usuario-routing.module';

@NgModule({
  declarations: [
    UsuarioListComponent,
    UsuarioCrudComponent
  ],
  imports: [
    CommonModule,
    FormsModule,
    RouterModule,
    UsuarioRoutingModule
  ],
  exports: [
    UsuarioListComponent,
    UsuarioCrudComponent
  ]
})
export class UsuarioModule { }
