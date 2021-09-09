import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class OrderService {
  constructor(private httpClient: HttpClient) {}

  localhost = environment.apiUrl + 'order/api/v1/orders';

  saveOrder(order: any) {
    return this.httpClient.post(this.localhost, order, httpOptions);
  }
  getAllOrders() {
    return this.httpClient.get(this.localhost);
  }
  getOrderById(id: number) {
    return this.httpClient.get(this.localhost + '/' + id);
  }
  getOrderByUserId(userId: string) {
    return this.httpClient.get(this.localhost + '/user/' + userId);
  }
  deleteOrder(id: string) {
    return this.httpClient.delete(this.localhost + '/' + id);
  }
  updateOrder(order: any) {
    return this.httpClient.patch(this.localhost, order, httpOptions);
  }
}
