import { PostProductService } from './../services/post-product.service';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, Validators } from '@angular/forms';
import { Product } from '../models/Product';
import { Router } from '@angular/router';

@Component({
  selector: 'app-Post',
  templateUrl: './Post.component.html',
  styleUrls: ['./Post.component.css']
})
export class PostComponent implements OnInit {

  form;
  fileToUpload: File;
  message = '';
  product: Product;

  constructor(private fb: FormBuilder, private router: Router, public postProductService: PostProductService, public dialogRef: MatDialogRef<DashboardComponent>) {
    this.product = new Product;
  }

  ngOnInit() {
    this.form = this.fb.group({
      title: ['', Validators.required],
      category: ['', Validators.required],
      description: [''],
      price: ['', Validators.required]
    })
  }

  cancel(): void {
    this.dialogRef.close();
  }

  submit() {
    if (this.form.value.title === '' || this.form.value.category === '' || this.form.value.price === '') {
      this.message = 'Fields should not be empty!!! Please verify details.';
    }
    else {
      this.product.title = this.form.get("title").value
      this.product.category = this.form.get("category").value
      this.product.description = this.form.get("description").value
      this.product.price = this.form.get("price").value
      this.postProductService.saveProduct(this.product).subscribe({
        next: res => {
          this.message = "The post was added successfully!"
          setTimeout(() => this.cancel(), 1000);
        },
        error: error => {
          this.message = "Failed to add product!";
          console.log(error);
        }
      });
    }
  }

  handleFileInput(files: FileList) {
    this.fileToUpload = files.item(0);
  }
}
