// src/app/app.module.ts

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';

import { MainLayoutModule } from './layout/main-layout/main-layout.module';  // <-- Importa el módulo
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

@NgModule({
  declarations: [
    AppComponent
    // No necesitas declarar MainLayoutComponent aquí, porque ya está declarado en MainLayoutModule
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule,
    MainLayoutModule    // <-- Agrégalo aquí
  ],
  providers: [AuthGuard, RoleGuard],
  bootstrap: [AppComponent]
})
export class AppModule { }
