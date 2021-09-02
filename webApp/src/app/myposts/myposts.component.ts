import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Product } from '../models/Product';
import { AuthenticationService } from '../services/authentication.service';
import { ProductService } from '../services/product.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-myposts',
  templateUrl: './myposts.component.html',
  styleUrls: [
    './myposts.component.css']
})

export class MypostsComponent implements OnInit {

  productList: Product[] = [];
  userId: number;

  constructor(private productService: ProductService, private authenticationService: AuthenticationService, private userService: UserService, private router: Router) {
    this.userId = this.authenticationService.currentUserValue.id;
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts() {
    this.productService.getProductsByUserId(this.userId).subscribe((products) => {
      this.productList = products;
    })
  }
  deleteItem(value: any) {
    window.location.reload();
  }
}