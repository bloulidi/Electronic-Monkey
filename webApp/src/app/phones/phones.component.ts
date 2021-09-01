import { Component, OnInit } from '@angular/core';
import { Product } from '../models/Product';
import { AuthenticationService } from '../services/authentication.service';
import { ProductService } from '../services/product.service';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-phones',
  templateUrl: './phones.component.html',
  styleUrls: ['./phones.component.css']
})
export class PhonesComponent implements OnInit {

  productList: Product[] = [];
  userId:number;

  constructor(private productService: ProductService, 
              private authenticationService: AuthenticationService, 
              private userService: UserService) {
    let email = this.authenticationService.currentUserValue.email;
    this.userId = this.authenticationService.currentUserValue.id;
    console.log("this is the email:" + email);
    console.log("this is the ID:" + this.userId);
  }

  ngOnInit(): void {
    this.loadProducts();
  }

  loadProducts() {
    console.log("userId in load products is:" + this.userId);
    this.productService.getProductsByCategory("Phones").subscribe((products) => {
      this.productList = products;
      console.log(this.productList);
      })
  }

}
