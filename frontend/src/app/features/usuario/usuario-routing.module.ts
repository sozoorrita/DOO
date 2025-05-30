import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { UsuarioCrudComponent } from './usuario-crud/usuario-crud.component';

const routes: Routes = [
  { path: '', component: UsuarioCrudComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UsuarioRoutingModule {}
