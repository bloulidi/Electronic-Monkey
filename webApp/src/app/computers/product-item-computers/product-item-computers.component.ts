import { ProductService } from 'src/app/services/product.service';
import { Component, Input, OnInit } from '@angular/core';
import { OrderProduct } from 'src/app/models/OrderProduct';
import { Product } from 'src/app/models/Product';
import { AuthenticationService } from 'src/app/services/authentication.service';

@Component({
  selector: 'app-product-item-computers',
  templateUrl: './product-item-computers.component.html',
  styleUrls: ['./product-item-computers.component.css']
})
export class ProductItemComputersComponent implements OnInit {

  @Input() productItem: Product;

  retrievedImage = '';
  productOrdersArray: OrderProduct[] = [];
  orderProduct: OrderProduct = new OrderProduct();
  isHidden: boolean;
  hiddenText: string;
  isAdmin: boolean;

  constructor(private authenticationService: AuthenticationService, private productService: ProductService) {
  }

  ngOnInit(): void {
    this.retrievedImage = 'data:' + this.productItem.photo.type + ';base64,' + this.productItem.photo.image;
    this.isHidden = this.productItem.hidden;
    this.isAdmin = this.authenticationService.currentUserValue.admin;
    this.hiddenText = this.isHidden ? "Unhide": "Hide";
  }

  handleAddToCart() {
    let productOrders = localStorage.getItem("productOrders");
    if (productOrders) {
      this.productOrdersArray = JSON.parse(productOrders);
    }
    this.orderProduct.product = this.productItem;
    this.orderProduct.quantity = 1;
    this.orderProduct.totalPrice = this.productItem.price;
    let index = this.productOrdersArray.findIndex(orderProduct => orderProduct.product.id === this.productItem.id);
    if(index > -1){
      this.productOrdersArray[index].quantity++;
    } else {
      this.productOrdersArray.push(this.orderProduct);
    }
    localStorage.setItem("productOrders", JSON.stringify(this.productOrdersArray));
    window.location.reload();
  }

  toggleHidden(productItem) {
    if(productItem.hidden){
      productItem.hidden = false;
      this.isHidden = false;
      this.hiddenText = "Hide";
    }
    else{
      productItem.hidden = true;
      this.isHidden = true;
      this.hiddenText = "Unhide";
    }
    this.productService.updateProductWithoutImage(productItem).subscribe({
      error: error => console.error(error)
    })
  }
}