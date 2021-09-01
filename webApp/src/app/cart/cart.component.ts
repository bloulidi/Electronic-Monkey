import { OrderProduct } from './../models/OrderProduct';
import { OrderService } from './../services/order.service';
import { Component, OnInit } from '@angular/core';
import { Order } from '../models/Order';

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
  retrievedImage = '';
  subTotal = 0;

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.order = new Order();
    this.orderProducts = JSON.parse(localStorage.getItem("productOrders"));
    console.log(this.orderProducts);
    this.calculateSubTotal();
    }

  handleMinus(orderProduct) {
    if(orderProduct.quantity == 1){
      orderProduct.quantity = 1;
    }
    else{
      orderProduct.quantity--;
      orderProduct.total = Math.round((orderProduct.quantity * orderProduct.product.price) * 100) / 100;
    }
  }

  handlePlus(orderProduct) {
    orderProduct.quantity++;
    orderProduct.total = Math.round((orderProduct.quantity * orderProduct.product.price) * 100) / 100;
  }

  remove(product){
    console.log("FIRST" + this.orderProducts);
    let index = this.orderProducts.indexOf(product);
    this.orderProducts.splice(index, 1);
    localStorage.setItem("productOrders", JSON.stringify(this.orderProducts));
    console.log("LAST" + this.orderProducts);
  }
  
  onCheckout(){
    console.log("Checkout page")
  }

  calculateSubTotal(){
    this.subTotal = 0;
    this.orderProducts.forEach(orderProduct => {
      this.subTotal += orderProduct.total;
    });
    this.subTotal = Math.round(this.subTotal * 100) / 100;
  }
}
