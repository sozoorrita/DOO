// src/app/features/forma-pago/forma-pago.module.ts
import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { FormaPagoRoutingModule } from './forma-pago-routing.module';
import { FormaPagoListComponent } from './pages/forma-pago-list/forma-pago-list.component';
import { FormaPagoFormComponent } from './pages/forma-pago-form/forma-pago-form.component';

@NgModule({
  declarations: [
    FormaPagoListComponent,
    FormaPagoFormComponent
  ],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    FormaPagoRoutingModule
  ]
})
export class FormaPagoModule { }
