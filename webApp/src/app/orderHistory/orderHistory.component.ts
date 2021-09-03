import { Product } from './../models/Product';
import { Component, OnInit } from '@angular/core';
import { OrderService } from './../services/order.service';

@Component({
  selector: 'app-orderHistory',
  templateUrl: './orderHistory.component.html',
  styleUrls: ['./orderHistory.component.css']
})
export class OrderHistoryComponent implements OnInit {
  orders: any

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.orderService.getAllOrders().subscribe({
      next: data => {
        this.orders = data;
        console.log(data);
      },
      error: error => {
          console.error(error);
      }
    });
  }
}