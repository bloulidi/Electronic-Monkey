import { OrderProduct } from './../models/OrderProduct';
import { OrderService } from './../services/order.service';
import { Component, OnInit } from '@angular/core';
import { Order } from '../models/Order';

export interface productInCart {
  imageURL: string;
  title: string;
  price: number;
  quantity: number;
  total: number;
}

let order: productInCart[] = [
  {price: 999.99, title: 'Dell laptop', imageURL: 'https://i.dell.com/is/image/DellContent//content/dam/global-site-design/product_images/dell_client_products/cloud_client_computing/wyse_5470/media_gallery/mobile_thin_client/laptop_wyse_14_5470_gallery_4.psd?fmt=pjpg&amp;pscan=auto&amp;scl=1&amp;hei=402&amp;wid=547&amp;qlt=85,0&amp;resMode=sharp2&amp;op_usm=1.75,0.3,2,0&amp;size=547,402', quantity: 1, total: 999.99},
  {price: 699.99, title: 'Iphone X', imageURL: 'https://images.ctfassets.net/t00ajdlq0g9p/6wlNEtbeT1azgd03aCn1BF/81d3c8753b1adaca76eed932815b5089/iPhone12-Pro-Max-blue-01.png', quantity: 2, total: 1399.98},
  {price: 19.99, title: 'Lightning cable', imageURL: 'https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/MX0K2?wid=1144&hei=1144&fmt=jpeg&qlt=80&.v=1618617117000', quantity: 2, total: 39.98},
];

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.css']
})
export class CartComponent implements OnInit {

  order: Order;
  total: number;
  orderProducts: OrderProduct[];

  constructor(private orderService: OrderService) { }

  ngOnInit() {
    this.order = new Order();
    this.orderProducts = JSON.parse(localStorage.getItem("orderProducts"));
    console.log(this.orderProducts)
  }

  displayedColumns: string[] = ['imageURL', 'title', 'price', 'quantity', 'total'];
  dataSource = order;

  handleMinus(product) {
    if(product.quantity == 0){
      product.quantity = 0;
    }
    else{
      product.quantity--;
      product.total = Math.round((product.total - product.price) * 100) / 100;
    }
  }

  handlePlus(product) {
    product.quantity++;
    product.total = Math.round((product.total + product.price) * 100) / 100;
  }

  remove(product){
    let index = this.dataSource.indexOf(product);
    this.dataSource.splice(index, 1);
  }
  
  onCheckout(){
    console.log("Checkout page")
  }
}
