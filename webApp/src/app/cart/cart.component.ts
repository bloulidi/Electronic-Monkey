import { OrderProduct } from './../models/OrderProduct';
import { OrderService } from './../services/order.service';
import { Component, OnInit } from '@angular/core';
import { Order } from '../models/Order';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  order: Order;
  total: number;
  orderProducts: OrderProduct[];
  displayedColumns: string[] = ['imageURL', 'title', 'price', 'quantity', 'total'];
  subTotal = 0;
  message = '';

  constructor(private orderService: OrderService, private authenticationService: AuthenticationService) { }

  ngOnInit() {
    this.order = new Order();
    this.orderProducts = JSON.parse(localStorage.getItem("productOrders"));
    this.calculateSubTotal();
  }

  handleMinus(orderProduct) {
    if (orderProduct.quantity == 1) {
      orderProduct.quantity = 1;
    }
    else {
      orderProduct.quantity--;
      orderProduct.total = Math.round((orderProduct.quantity * orderProduct.product.price) * 100) / 100;
      this.order.totalPrice = orderProduct.total;
    }
  }

  handlePlus(orderProduct) {
    orderProduct.quantity++;
    orderProduct.total = Math.round((orderProduct.quantity * orderProduct.product.price) * 100) / 100;
  }

  remove(product) {
    let index = this.orderProducts.indexOf(product);
    this.orderProducts.splice(index, 1);
    localStorage.setItem("productOrders", JSON.stringify(this.orderProducts));
  }

  onCheckout() {
    this.order.orderProducts = this.orderProducts;
    this.order.userId = this.authenticationService.currentUserValue.id;
    this.orderService.saveOrder(this.order).subscribe({
      next: data => {
        this.message = "Checked out Successfully!";
        this.total = 0;
        this.orderProducts = [];
        localStorage.removeItem('productOrders');
      },
      error: error => {
        if (error.status == '409') {
          this.message = "Order already exists!";
          console.error("Order already exists!", error);
        } else {
          this.message = "Failed to checkout!";
          console.error("Failed to checkout!", error);
        }
      }
    });
  }

  calculateSubTotal() {
    this.subTotal = 0;
    this.orderProducts.forEach(orderProduct => {
      this.subTotal += orderProduct.total;
    });
    this.subTotal = Math.round(this.subTotal * 100) / 100;
    // this.order.totalPrice = this.subTotal;
  }
}