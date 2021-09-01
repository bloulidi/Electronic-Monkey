import { ProductService } from '../services/product.service';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Product } from '../models/Product';
import { Router } from '@angular/router';
import { Photo } from '../models/Photo';
import { AuthenticationService } from '../services/authentication.service';

@Component({
  selector: 'app-Post',
  templateUrl: './Post.component.html',
  styleUrls: ['./Post.component.css']
})
export class PostComponent implements OnInit {

  form: FormGroup;
  fileToUpload: File;
  message = '';
  product: Product;
  photo: Photo;
  retrievedImage = '';
  isProductAdded:boolean = false;

  constructor(private fb: FormBuilder, private router: Router, public productService: ProductService, public dialogRef: MatDialogRef<DashboardComponent>, private authenticationService: AuthenticationService) {
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
    if (this.form.value.title === ''){
      this.message = 'Title is required';
    } else if (this.form.value.category === ''){
      this.message = 'Category is required';
    } else if (this.form.value.price === '') {
      this.message = 'Price is required';
    } else if (this.fileToUpload == null){
      this.message = "Please select a photo before submitting.";
    } else if(!this.fileToUpload.type.startsWith("image/")){
      this.message = "File selected is not an image!"
    } else if (this.form.invalid) {
      this.message = "Invalid field(s)!";
    } else {
      this.product.title = this.form.value.title
      this.product.category = this.form.value.category
      this.product.description = this.form.value.description
      this.product.price = this.form.value.price
      this.product.photo.title = this.fileToUpload.name
      this.product.userId = this.authenticationService.currentUserValue.id;
      this.productService.saveProduct(this.product, this.fileToUpload).subscribe({
        next: (res: any) => {
          this.message = "Post added successfully!"
          this.isProductAdded = true;
          setTimeout(() => this.cancel(), 1000);

          // Show picture
          //this.retrievedImage = 'data:' + res.photo.type + ';base64,' + res.photo.image.data;
        },
        error: error => {
          if(error.status == '409') {
            this.message = "This product already exists!";
            console.error("This product already exists!", error);
          } else {
            this.message = "Failed to add product!";
            console.error("Failed to add product!", error);
          }
        }
      });
    }
  }

  handleFileInput(event) {
    this.fileToUpload = event.target.files[0];
  }
}
