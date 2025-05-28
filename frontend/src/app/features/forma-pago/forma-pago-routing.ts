// src/app/features/forma-pago/forma-pago-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormaPagoListComponent } from './pages/forma-pago-list/forma-pago-list.component';
import { FormaPagoFormComponent } from './pages/forma-pago-form/forma-pago-form.component';

const routes: Routes = [
  { path: '', component: FormaPagoListComponent },
  { path: 'nuevo', component: FormaPagoFormComponent },
  { path: 'editar/:id', component: FormaPagoFormComponent }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class FormaPagoRoutingModule { }
