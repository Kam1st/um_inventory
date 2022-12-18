import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'
import { AppComponent } from './app.component';
import { StockItemComponent } from './stock-item/stock-item.component';
import { StockItemService } from './stock-item.service';
import { InventoryItemService } from './inventory-item.service';


@NgModule({
  declarations: [
    AppComponent,
    StockItemComponent,
    InventoryItemComponent,
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [StockItemService],
  providers: [InventoryItemService],
  bootstrap: [AppComponent]
})
export class AppModule { }
