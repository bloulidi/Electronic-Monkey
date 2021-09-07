import { Component, OnInit } from '@angular/core';
import { Product } from '../models/Product';
import { AuthenticationService } from '../services/authentication.service';
import { ProductService } from '../services/product.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.css'],
})
export class CategoryComponent implements OnInit {
  productList: Product[] = [];
  userId: number;
  category: string;

  constructor(
    protected productService: ProductService,
    protected authenticationService: AuthenticationService,
    protected userService: UserService
  ) {
    this.userId = this.authenticationService.currentUserValue.id;
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts() {
    this.productService
      .getProductsByCategory(this.category)
      .subscribe((products) => {
        this.productList = products;
      });
  }
}
