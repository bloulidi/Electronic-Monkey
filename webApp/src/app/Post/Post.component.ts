import { PostProductService } from './../services/post-product.service';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, Validators } from '@angular/forms';
import { Product } from '../models/Product';
import { Router } from '@angular/router';
import { Photo } from '../models/Photo';

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
  photo: Photo;
  retrievedImage = '';

  constructor(private fb: FormBuilder, private router: Router, public postProductService: PostProductService, public dialogRef: MatDialogRef<DashboardComponent>) {
    this.product = new Product;
    this.photo = new Photo;
    this.product.photo = this.photo;
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
    } else if (this.fileToUpload == null){
      this.message = "Please select a photo before submitting.";
    }
    else if(!this.fileToUpload.type.startsWith("image/")){
      this.message = "File selected is not an image!"
    }
    else if (this.form.invalid) {
      this.message = "Invalid field(s)!";
    }
    else {
      this.product.title = this.form.get("title").value
      this.product.category = this.form.get("category").value
      this.product.description = this.form.get("description").value
      this.product.price = this.form.get("price").value
      this.product.photo.title = this.fileToUpload.name
      this.postProductService.saveProductWithImage(this.product, this.fileToUpload).subscribe({
        next: (res: any) => {
          this.message = "Post added successfully!"
          setTimeout(() => this.cancel(), 1000);

          // Show picture
          //this.retrievedImage = 'data:' + res.photo.type + ';base64,' + res.photo.image.data;
        },
        error: error => {
          this.message = "Failed to add product!";
          console.log(error);
        }
      });
    }
  }

  handleFileInput(event) {
    this.fileToUpload = event.target.files[0];
  }
}
