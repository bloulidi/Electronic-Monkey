import { Component, OnInit } from '@angular/core';
import { Product } from '../models/Product';

@Component({
  selector: 'app-myposts',
  templateUrl: './myposts.component.html',
  styleUrls: [
  './myposts.component.css']
})
export class MypostsComponent implements OnInit {

  productList: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getProducts().subscribe((products) => {
      this.productList = products;
    })
  }

}
