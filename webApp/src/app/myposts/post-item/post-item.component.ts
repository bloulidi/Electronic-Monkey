import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Product } from 'src/app/models/Product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-post-item',
  templateUrl: './post-item.component.html',
  styleUrls: ['./post-item.component.css']
})
export class PostItemComponent implements OnInit {

  @Input() productItem: Product;

  retrievedImage = '';
  @Output() newItemEvent = new EventEmitter<string>();

  constructor(private productService: ProductService) {
  }

  ngOnInit(): void {
    this.retrievedImage = 'data:' + this.productItem.photo.type + ';base64,' + this.productItem.photo.image.data;
  }

  async deletePost(id: any) {
    this.productService.deleteProduct(id).subscribe(data => {
      this.newItemEvent.emit("product deleted");
    });
  }
}