import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule }   from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { MaterialModule }   from './material.module';

import { AppComponent }            from './app.component';
import { LoginComponent }          from './auth/login/login.component';
import { RegisterComponent }       from './auth/register/register.component';
import { OpenSessionComponent }    from './dashboard/open-session.component';
import { TableMapComponent }       from './tables/table-map.component';
import { QuickSaleComponent }      from './pos/quick-sale.component';
import { TableSaleComponent }      from './pos/table-sale.component';
import { InvoiceViewComponent }    from './invoice/invoice-view.component';
import { InventoryListComponent }  from './inventory/inventory-list.component';
import { SessionReportComponent }  from './reports/session-report.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    RegisterComponent,
    OpenSessionComponent,
    TableMapComponent,
    QuickSaleComponent,
    TableSaleComponent,
    InvoiceViewComponent,
    InventoryListComponent,
    SessionReportComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    MaterialModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
