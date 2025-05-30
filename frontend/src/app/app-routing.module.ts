import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';
import { AuthGuard }          from './guards/auth.guard';
import { SessionGuard }       from './guards/session.guard';
import { NoSessionGuard }     from './guards/no-session.guard';

const routes: Routes = [
  // 1) Siempre ir primero a login
  { path: '', redirectTo: 'auth/login', pathMatch: 'full' },

  // 2) Rutas de autenticación
  {
    path: 'auth',
    loadChildren: () =>
      import('./features/auth/auth.module').then(m => m.AuthModule)
  },

  // 3) Layout protegido tras login
  {
    path: '',
    component: MainLayoutComponent,
    canActivate: [AuthGuard],
    children: [
      { path: 'session',       loadChildren: () => import('./features/session/session.module').then(m => m.SessionModule) },
      { path: 'mesas',         loadChildren: () => import('./features/mesa/mesa.module').then(m => m.MesaModule),          canActivate: [SessionGuard] },
      { path: 'venta-rapida',  loadChildren: () => import('./features/venta-rapida/venta-rapida.module').then(m => m.VentaRapidaModule), canActivate: [SessionGuard] },
      { path: 'usuarios',      loadChildren: () => import('./features/usuario/usuario.module').then(m => m.UsuarioModule),      canActivate: [SessionGuard] },
      { path: 'inventario',    loadChildren: () => import('./features/inventario/inventario.module').then(m => m.InventarioModule), canActivate: [SessionGuard] },
      { path: 'reportes',      loadChildren: () => import('./features/reportes/reportes.module').then(m => m.ReportesModule),   canActivate: [NoSessionGuard] }
    ]
  },

  // 4) Cualquier otra → login
  { path: '**', redirectTo: 'auth/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
