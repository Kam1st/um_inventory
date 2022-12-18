import { Component } from '@angular/core';
import { StockItem } from './stock-item';
import { StockItemService } from './stock-item.service';
import { HttpErrorResponse } from '@angular/common/http';
import {InventoryItem} from "./inventory-item";
import {InventoryItemService} from "./inventory-item.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
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

  public InventoryItems: InventoryItem[];

  constructor(private inventoryItemService: InventoryItemService) { }

  ngOnInit() {
    this.getInventoryItems();
  }

  public getInventoryItems(): void {
    this.inventoryItemService.getInventoryItems().subscribe(
      (response: InventoryItem[]) => {
        this.InventoryItems = response;
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );
  }

}
