import { OrderProduct } from './../../models/OrderProduct';
import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';

@Component({
  selector: 'app-product-item-phones',
  templateUrl: './product-item-phones.component.html',
  styleUrls: ['./product-item-phones.component.css']
})
export class ProductItemPhonesComponent implements OnInit {

  @Input() productItem: Product;

  retrievedImage = '';
  productOrdersArray: OrderProduct[] = [];

  constructor() {
  }

  ngOnInit(): void {
    console.log("Product received by parent" + this.productItem);
    this.retrievedImage = 'data:' + this.productItem.photo.type + ';base64,' + this.productItem.photo.image.data;
  }

  handleAddToCart() {
    let productOrders = localStorage.getItem("productOrders");
    if (productOrders) {
      JSON.parse(productOrders).push(this.productItem);
    }
    else {
      localStorage.setItem("productOrders", JSON.stringify(this.productOrdersArray));
    }
  }
}
