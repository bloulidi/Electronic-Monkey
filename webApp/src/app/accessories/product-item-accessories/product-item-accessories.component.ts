import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';

@Component({
  selector: 'app-product-item-accessories',
  templateUrl: './product-item-accessories.component.html',
  styleUrls: ['./product-item-accessories.component.css']
})
export class ProductItemAccessoriesComponent implements OnInit {

  @Input() productItem: Product;

  retrievedImage = '';

  constructor(   ) { 
  }

  ngOnInit(): void {
    console.log("Product received by parent"+ this.productItem);
    this.retrievedImage ='data:' + this.productItem.photo.type + ';base64,' + this.productItem.photo.image.data; 
  }
  
  handleAddToCart() {

  }

}
