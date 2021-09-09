import { Component, OnInit } from '@angular/core';
import { Order } from '../models/Order';
import { AuthenticationService } from '../services/authentication.service';
import { OrderProduct } from './../models/OrderProduct';
import { OrderService } from './../services/order.service';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css'],
})
export class CartComponent implements OnInit {
  order: Order;
  total: number;
  orderProducts: OrderProduct[];
  displayedColumns: string[] = [
    'imageURL',
    'title',
    'price',
    'quantity',
    'total',
  ];
  subTotal = 0;
  message = '';

  constructor(
    private orderService: OrderService,
    private authenticationService: AuthenticationService
  ) {}

  ngOnInit() {
    if (localStorage.getItem('productOrders')) {
      this.orderProducts = JSON.parse(localStorage.getItem('productOrders'));
      this.calculateSubTotal();
    }
  }

  handleMinus(orderProduct: OrderProduct) {
    if (orderProduct.quantity == 1) {
      orderProduct.quantity = 1;
    } else {
      orderProduct.quantity--;
      orderProduct.totalPrice =
        Math.round(orderProduct.quantity * orderProduct.product.price * 100) /
        100;
    }
  }

  handlePlus(orderProduct: OrderProduct) {
    orderProduct.quantity++;
    orderProduct.totalPrice =
      Math.round(orderProduct.quantity * orderProduct.product.price * 100) /
      100;
  }

  remove(orderProduct: OrderProduct) {
    let index = this.orderProducts.indexOf(orderProduct);
    this.orderProducts.splice(index, 1);
    localStorage.setItem('productOrders', JSON.stringify(this.orderProducts));
  }

  onCheckout() {
    if (!this.orderProducts || !this.orderProducts.length) {
      this.message = 'Please add an item before checking out!';
      return;
    }
    this.order = new Order();
    this.order.orderProducts = this.orderProducts;
    this.order.userId = this.authenticationService.currentUserValue.id;
    this.calculateSubTotal();
    this.order.totalOrderPrice = this.subTotal;
    this.subTotal = 0;
    this.orderService.saveOrder(this.order).subscribe({
      next: (data) => {
        this.message = 'Checked out successfully!';
        this.total = 0;
        this.orderProducts = [];
        localStorage.setItem(
          'productOrders',
          JSON.stringify(this.orderProducts)
        );
      },
      error: (error) => {
        if (error.status == '409') {
          this.message = error.error;
          console.error(error);
        } else {
          this.message = 'Failed to checkout!';
          console.error('Failed to checkout!', error);
        }
      },
    });
  }

  calculateSubTotal() {
    this.subTotal = 0;
    if (this.orderProducts) {
      this.orderProducts.forEach((orderProduct) => {
        orderProduct.totalPrice =
          Math.round(orderProduct.quantity * orderProduct.product.price * 100) /
          100;
        this.subTotal += orderProduct.totalPrice;
      });
      this.subTotal = Math.round(this.subTotal * 100) / 100;
    }
  }
}
