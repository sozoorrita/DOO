import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule } from '@angular/forms';
import { ProductosRoutingModule } from './productos-routing.module';
import { ProductoListComponent } from './pages/producto-list/producto-list.component';
import { ProductoFormComponent } from './pages/producto-form/producto-form.component';
import { MaterialModule } from '.././../material.module';

@NgModule({
  declarations: [ProductoListComponent, ProductoFormComponent],
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ProductosRoutingModule,
    MaterialModule
  ]
})
export class ProductosModule {}
