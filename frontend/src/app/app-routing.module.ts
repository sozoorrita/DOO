import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SessionGuard } from './guards/session.guard';

const routes: Routes = [
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },

  // Lazy loading para el módulo de autenticación (login, register)
  {
    path: 'auth',
    loadChildren: () =>
      import('./features/auth/auth.module').then(m => m.AuthModule)
  },

  // Lazy loading para el módulo de categorías, PROTEGIDO con SessionGuard
  {
    path: 'categorias',
    loadChildren: () =>
      import('./features/categoria/categoria.module').then(m => m.CategoriaModule),
    canActivate: [SessionGuard] // ⬅️ Protección general para todas las rutas hijas
  },

  // Otras rutas con lazy loading y protección (ejemplo inventario)
  // {
  //   path: 'inventario',
  //   loadChildren: () =>
  //     import('./features/inventario/inventario.module').then(m => m.InventarioModule),
  //   canActivate: [SessionGuard]
  // },

  // Wildcard para rutas no encontradas
  { path: '**', redirectTo: 'auth/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
