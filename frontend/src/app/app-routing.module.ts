import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

// Guards
import { AuthGuard } from './guards/auth.guard';
import { RoleGuard } from './guards/role.guard';

const routes: Routes = [
  // Lazy loading de features principales:
  {
    path: 'login',
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'register',
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'inventario',
    loadChildren: () => import('./features/inventario/inventario.module').then(m => m.InventarioModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'mesas',
    loadChildren: () => import('./features/mesa/mesa.module').then(m => m.MesaModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'usuarios',
    loadChildren: () => import('./features/usuario/usuario.module').then(m => m.UsuarioModule),
    canActivate: [RoleGuard],
    data: { role: 'UUID_ADMIN' } // Reemplaza por el UUID real del rol admin
  },
  {
    path: 'reportes',
    loadChildren: () => import('./features/reportes/reportes.module').then(m => m.ReportesModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'session',
    loadChildren: () => import('./features/session/session.module').then(m => m.SessionModule),
    canActivate: [AuthGuard]
  },
  {
    path: 'venta-rapida',
    loadChildren: () => import('./features/venta-rapida/venta-rapida.module').then(m => m.VentaRapidaModule),
    canActivate: [AuthGuard]
  },
  { path: '', redirectTo: '/login', pathMatch: 'full' },
  { path: '**', redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
