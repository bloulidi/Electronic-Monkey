import { Component, Input, OnInit } from '@angular/core';
import { OrderProduct } from 'src/app/models/OrderProduct';
import { Product } from 'src/app/models/Product';

@Component({
  selector: 'app-product-item-computers',
  templateUrl: './product-item-computers.component.html',
  styleUrls: ['./product-item-computers.component.css']
})
export class ProductItemComputersComponent implements OnInit {

  @Input() productItem: Product;

  retrievedImage = '';
  productOrdersArray: OrderProduct[] = [];

  constructor(   ) { 
  }

  ngOnInit(): void {
    console.log("Product received by parent"+ this.productItem);
    this.retrievedImage ='data:' + this.productItem.photo.type + ';base64,' + this.productItem.photo.image.data; 
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
