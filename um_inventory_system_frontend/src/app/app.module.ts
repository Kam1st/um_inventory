import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'
import { AppComponent } from './app.component';
import { StockItemComponent } from './stock-item/stock-item.component';
import { StockItemService } from './stock-item.service';


@NgModule({
  declarations: [
    AppComponent,
    StockItemComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [StockItemService],
  bootstrap: [AppComponent]
})
export class AppModule { }
