import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsuarioListComponent } from './usuario-list/usuario-list.component';
import { UsuarioCrudComponent } from './usuario-crud/usuario-crud.component';

const routes: Routes = [
  {
    path: 'list',
    component: UsuarioListComponent
  },
  {
    path: 'crud',
    component: UsuarioCrudComponent
  },
  {
    path: 'crud/:id',
    component: UsuarioCrudComponent
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule { }
