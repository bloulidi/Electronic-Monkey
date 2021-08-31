import { Product } from './../models/Product';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-orderHistory',
  templateUrl: './orderHistory.component.html',
  styleUrls: ['./orderHistory.component.css']
})
export class OrderHistoryComponent implements OnInit {
  
  orderProducts1 = [
    { image: "https://www.notebookcheck.net/uploads/tx_nbc2/MicrosoftSurfaceLaptop3-15__1__01.JPG", name: "MacBook 2021", price: 1100},
    { image: "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/iphone-xr-white-select-201809?wid=940&hei=1112&fmt=p-jpg&qlt=95&.v=1551226036668", name: "Iphone X", price: 700},
    { image: "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/MD810?wid=572&hei=572&fmt=jpeg&qlt=95&.v=1532627474032", name: "Apple 5W USB Power Adapter", price: 25},
  ]

  orderProduct2 = [
    { image: "https://www.notebookcheck.net/uploads/tx_nbc2/MicrosoftSurfaceLaptop3-15__1__01.JPG", name: "MacBook 2021", price: 1100},
    { image: "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/iphone-xr-white-select-201809?wid=940&hei=1112&fmt=p-jpg&qlt=95&.v=1551226036668", name: "Iphone X", price: 700},
    { image: "https://store.storeimages.cdn-apple.com/4982/as-images.apple.com/is/MD810?wid=572&hei=572&fmt=jpeg&qlt=95&.v=1532627474032", name: "Apple 5W USB Power Adapter", price: 25},
  ]
  
  order = [
    { orderDate: "12/06/21", total: 3000, orderNumber: "H1H1H2H2", products: this.orderProducts1},
    { orderDate: "12/08/21", total: 3000, orderNumber: "H1H1H2H2", products: this.orderProduct2}
  ]

  constructor() { }

  ngOnInit() {
  }

}
