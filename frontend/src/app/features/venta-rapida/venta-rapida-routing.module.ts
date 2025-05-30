import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VentaRapidaComponent } from './venta-rapida/venta-rapida.component';
import { AuthGuard } from '../../guards/auth.guard';

const routes: Routes = [
  {
  path: 'venta-rapida',
  loadChildren: () =>
    import('./venta-rapida.module').then(m => m.VentaRapidaModule),
  canActivate: [AuthGuard]
}

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class VentaRapidaRoutingModule { }
