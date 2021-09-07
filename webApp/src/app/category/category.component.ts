import { Component, OnInit } from '@angular/core';
import { Product } from '../models/Product';
import { AuthenticationService } from '../services/authentication.service';
import { ProductService } from '../services/product.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css']
})
export class CategoryComponent implements OnInit {

  productList: Product[] = [];
  userId: number;
  category: string;

  constructor(public productService: ProductService,
    public authenticationService: AuthenticationService,
    public userService: UserService) {
    this.userId = this.authenticationService.currentUserValue.id;
    console.log("userId:" + this.userId)
  }

  ngOnInit(): void {
    console.log("ngOnInit A")
    this.loadProducts();
  }

  loadProducts() {
    console.log("loadProducts A")
    this.productService.getProductsByCategory(this.category).subscribe((products) => {
      this.productList = products;
    })
  }
}