import { Component } from '@angular/core';
import { StockItem } from './stock-item';
import { StockItemService } from './stock-item.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  // title = 'um_inventory_system_frontend';

  public StockItems: StockItem[];

  constructor(private stockItemService: StockItemService) { }

  ngOnInit() {
    this.getStockItems();
  }

  public getStockItems(): void {
    this.stockItemService.getStockItems().subscribe(
      (response: StockItem[]) => {
        this.StockItems = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}
