// src/app/app.module.ts

import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

// Módulos propios del proyecto
import { AuthModule } from './features/auth/auth.module';
import { CategoriaModule } from './features/categoria/categoria.module';
import { MainLayoutModule } from './layout/main-layout/main-layout.module';
import { AppRoutingModule } from './app-routing.module';

// Componente raíz
import { AppComponent } from './app.component';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,

    // Módulos de funcionalidad

    CategoriaModule,
    MainLayoutModule,

    // Módulo de enrutamiento (debe ir último para respetar el orden de rutas)
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
