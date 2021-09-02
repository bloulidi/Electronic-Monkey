import { OrderProduct } from './../../models/OrderProduct';
import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-product-item-phones',
  templateUrl: './product-item-phones.component.html',
  styleUrls: ['./product-item-phones.component.css']
})
export class ProductItemPhonesComponent implements OnInit {

  @Input() productItem: Product;

  retrievedImage = '';
  productOrdersArray: OrderProduct[] = [];
  orderProduct: OrderProduct = new OrderProduct();
  isHidden: boolean;

  constructor(private authenticationService: AuthenticationService) {
  }

  ngOnInit(): void {
    this.retrievedImage = 'data:' + this.productItem.photo.type + ';base64,' + this.productItem.photo.image.data;
    this.isHidden = this.authenticationService.currentUserValue.admin;
  }

  handleAddToCart() {
    let productOrders = localStorage.getItem("productOrders");
    if (productOrders) {
      this.productOrdersArray = JSON.parse(productOrders);
    }
    this.orderProduct.product = this.productItem;
    this.orderProduct.quantity = 1;
    this.orderProduct.totalPrice = this.productItem.price;
    this.productOrdersArray.push(this.orderProduct);
    localStorage.setItem("productOrders", JSON.stringify(this.productOrdersArray));
    window.location.reload();
  }
  hidePost(){
    
  }
}