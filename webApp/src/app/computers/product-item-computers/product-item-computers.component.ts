import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'src/app/models/Product';

@Component({
  selector: 'app-product-item-computers',
  templateUrl: './product-item-computers.component.html',
  styleUrls: ['./product-item-computers.component.css']
})
export class ProductItemComputersComponent implements OnInit {

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
