import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ShellComponent } from './layout/shell/shell.component';

const routes: Routes = [
  { path: '', redirectTo: 'auth/inicio-sesion', pathMatch: 'full' },
  {
    path: 'auth',
    loadChildren: () => import('./features/auth/auth.module').then(m => m.AuthModule)
  },
  {
    path: 'session',
    loadChildren: () => import('./features/session/session.module').then(m => m.SessionModule)
  },
  {
    path: '',
    component: ShellComponent,
    children: [
      {
        path: 'pos',
        loadChildren: () => import('./features/pos/pos.module').then(m => m.PosModule)
      },
      {
        path: 'inventario',
        loadChildren: () =>
          import('./features/inventario/inventario.module').then(m => m.InventarioModule)
      },
      {
        path: 'ventas',
        loadChildren: () => import('./features/ventas/ventas.module').then(m => m.VentasModule)
      },
      { path: '', redirectTo: 'pos/mesas', pathMatch: 'full' }
    ]
  },
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
