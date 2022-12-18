import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import {InventoryItem} from "./inventory-item";
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InventoryItemService {

  private apiServerUrl = "http://localhost:8080/inventory";

  constructor(private http: HttpClient) { }

  public getInventoryItems(): Observable<InventoryItem[]> {
    return this.http.get<InventoryItem>(`${this.apiServerUrl}`)
  }

  public addInventoryItem(inventoryItem: InventoryItem): Observable<InventoryItem> {
    return this.http.post<InventoryItem>(`${this.apiServerUrl}`, inventoryItem);
  }
}
