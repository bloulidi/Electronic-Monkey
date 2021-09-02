import { Component, OnInit } from '@angular/core';
import { Product } from '../models/Product';
import { AuthenticationService } from '../services/authentication.service';
import { ProductService } from '../services/product.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-computers',
  templateUrl: './computers.component.html',
  styleUrls: ['./computers.component.css']
})
export class ComputersComponent implements OnInit {

  productList: Product[] = [];
  userId: number;

  constructor(private productService: ProductService,
    private authenticationService: AuthenticationService,
    private userService: UserService) {
    this.userId = this.authenticationService.currentUserValue.id;
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getProductsByCategory("Computers").subscribe((products) => {
      this.productList = products;
    })
  }
}