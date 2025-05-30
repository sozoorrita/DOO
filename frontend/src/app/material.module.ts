// src/app/material.module.ts
import { NgModule } from '@angular/core';
import { MatButtonModule } from '@angular/material/button';
import { MatInputModule }  from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';

@NgModule({
  exports: [
    MatButtonModule,
    MatInputModule,
    MatSelectModule,
    // ...añade aquí los módulos de Material que uses
  ]
})
export class MaterialModule {}
