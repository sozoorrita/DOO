import { NgModule }             from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { LoginComponent }        from './auth/login/login.component';
import { RegisterComponent }     from './auth/register/register.component';
import { OpenSessionComponent }  from './dashboard/open-session.component';
import { TableMapComponent }     from './tables/table-map.component';
import { QuickSaleComponent }    from './pos/quick-sale.component';
import { TableSaleComponent }    from './pos/table-sale.component';
import { InvoiceViewComponent }  from './invoice/invoice-view.component';
import { InventoryListComponent }from './inventory/inventory-list.component';
import { SessionReportComponent }from './reports/session-report.component';

const routes: Routes = [
  { path: '',           redirectTo: '/login', pathMatch: 'full' },
  { path: 'login',      component: LoginComponent },
  { path: 'register',   component: RegisterComponent },
  { path: 'dashboard',  component: OpenSessionComponent },
  { path: 'tables',     component: TableMapComponent },
  { path: 'quick-sale', component: QuickSaleComponent },
  { path: 'sale/:tableId', component: TableSaleComponent },
  { path: 'invoice/:saleId', component: InvoiceViewComponent },
  { path: 'inventory',  component: InventoryListComponent },
  { path: 'reports/:sessionId', component: SessionReportComponent },
  { path: '**',         redirectTo: '/login' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
