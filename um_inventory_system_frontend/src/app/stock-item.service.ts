import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { StockItem } from './stock-item';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StockItemService {

  private baseUrl = "http://localhost:8080/stocks";

  constructor(private http: HttpClient) { }

  public getStockItems(): Observable<StockItem[]> {
    return this.http.get<StockItem[]>(`${this.baseUrl}`);
  }
}
