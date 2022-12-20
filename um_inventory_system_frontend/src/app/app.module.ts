import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'
import { AppComponent } from './app.component';
import { StockItemComponent } from './stock-item/stock-item.component';
import { StockItemService } from './stock-item.service';
import { AddStockItemComponent } from './stock-item/add-stock-item/add-stock-item.component';
import { StockItemModule } from './stock-item/stock-item.module';


@NgModule({
  declarations: [
    AppComponent,
    StockItemComponent,
    AddStockItemComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    StockItemModule
  ],
  providers: [StockItemService],
  bootstrap: [AppComponent]
})
export class AppModule { }
