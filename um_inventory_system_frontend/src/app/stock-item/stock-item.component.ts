import { Component, OnInit } from '@angular/core';
import { StockItemService } from '../stock-item.service';
import { StockItem } from '../stock-item';

@Component({
  selector: 'app-stock-item',
  templateUrl: './stock-item.component.html',
  styleUrls: ['./stock-item.component.css']
})
export class StockItemComponent implements OnInit {

  stockItems: StockItem[];
  constructor(private stockItemService: StockItemService) { }

  ngOnInit(): void {
    this.stockItemService.getStockItems().subscribe((data: StockItem[]) => {
      console.log(data);
      this.stockItems = data;
    });
  }

}
