import { Component, OnInit } from '@angular/core';
import { Product } from '../models/Product';
import { AuthenticationService } from '../services/authentication.service';
import { ProductService } from '../services/product.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-accessories',
  templateUrl: './accessories.component.html',
  styleUrls: ['./accessories.component.css']
})
export class AccessoriesComponent implements OnInit {

  productList: Product[] = [];
  userId: number;

  constructor(private productService: ProductService, private authenticationService: AuthenticationService) {
    this.userId = this.authenticationService.currentUserValue.id;
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getProductsByCategory("Accessories").subscribe((products) => {
      this.productList = products;
    })
  }
}