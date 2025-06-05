// src/app/features/auth/auth.module.ts

import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AuthRoutingModule } from './auth-routing.module';
import { LoginComponent } from './login/login.component';       // Componente standalone
import { RegisterComponent } from './register/register.component'; // Componente standalone

@NgModule({
  imports: [
    CommonModule,
    AuthRoutingModule,
    LoginComponent,      // ← Se importa directamente, en lugar de “declarar”
    RegisterComponent    // ← Igual aquí
  ]
})
export class AuthModule { }
