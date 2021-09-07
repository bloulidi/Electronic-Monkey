import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { ModalDismissReasons, NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { Photo } from 'src/app/models/Photo';
import { Product } from 'src/app/models/Product';
import { AuthenticationService } from 'src/app/services/authentication.service';
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
  
  closeResult = '';
  closeResultEdit='';

  form: FormGroup;
  fileToUpload: File;
  message = '';
  product: Product;
  photo: Photo;

  title: string = '';
  description: string = '';
  category: string = '';
  price: number;

  constructor(private productService: ProductService, private modalService: NgbModal,
    private fb: FormBuilder, private router: Router,
    private authenticationService: AuthenticationService) {
      this.product = new Product;
      this.photo = new Photo;
      this.product.photo = this.photo;

      this.form = this.fb.group({
        title: ['', Validators.required],
        category: ['', Validators.required],
        description: [''],
        price: ['', Validators.required]
      })
  }

  ngOnInit(): void {
    this.retrievedImage = 'data:' + this.productItem.photo.type + ';base64,' + this.productItem.photo.image;
   
    this.form.get('title').setValue(this.productItem.title);
    this.form.get('category').setValue(this.productItem.category);
    this.form.get('description').setValue(this.productItem.description);
    this.form.get('price').setValue(this.productItem.price);
    this.message = ""; 

    this.title = this.form.value.title;
    this.category = this.form.value.category;
    this.description = this.form.value.description;
    this.price = this.form.value.price;
  }

  openEdit(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-edit'}).result.then((result) => {
      this.closeResultEdit = `Closed with: ${result}`;
      if(this.closeResultEdit === "Closed with: Save click"){
      }
    }, (reason) => {
      this.closeResultEdit = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  handleFileInput(event) {
    this.fileToUpload = event.target.files[0];
  }

  checkSave(){
    if (this.form.value.title === '') {
      this.message = 'Title is required';
      return true;
    } else if (this.form.value.category === '') {
      this.message = 'Category is required';
      return true;
    } else if (this.form.value.price === '') {
      this.message = 'Price is required';
      return true;
    } else if (this.form.value.price < 1){
      this.message = "Please enter a valid price >= $1";
      return true;
    } else if (this.fileToUpload != null){
      if (!this.fileToUpload.type.startsWith("image/")) {
        this.message = "File selected is not an image!";
        return true;
      }
    } else if (this.form.invalid) {
      this.message = "Invalid field(s)!";
      return true;
    } else if(this.price== this.form.value.price && 
      this.title==this.form.value.title &&
      this.category == this.form.value.category &&
      this.description == this.form.value.description){
        return true;
    } else{
      this.message = "";
      return false;
    }
  }

  submit(){
    if(!this.checkSave()){
      this.productItem.title = this.form.value.title;
      this.productItem.category = this.form.value.category;
      this.productItem.description = this.form.value.description;
      this.productItem.price = this.form.value.price;
      this.productService.updateProduct(this.productItem, this.fileToUpload).subscribe({
        next: (res: any) => {
          this.retrievedImage = 'data:' + res.photo.type + ';base64,' + res.photo.image;
        },
        error: error => {
          console.error(error);
        }
      });
      window.location.reload();
    }
  }

  openRemove(content, id) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
      if(this.closeResult === "Closed with: Remove click"){
        this.deletePost(id);
      }
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }

  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

  async deletePost(id: any) {
    this.productService.deleteProduct(id).subscribe(data => {
      this.newItemEvent.emit("product deleted");
    }); 
  }
}