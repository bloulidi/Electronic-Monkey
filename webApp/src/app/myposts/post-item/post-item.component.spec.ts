import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs/internal/observable/of';
import { Photo } from 'src/app/models/Photo';
import { Product } from 'src/app/models/Product';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProductService } from 'src/app/services/product.service';

import { PostItemComponent } from './post-item.component';

describe('PostItemComponent', () => {
  let component: PostItemComponent;
  let fixture: ComponentFixture<PostItemComponent>;
  let productService: ProductService;
  let authenticationService: AuthenticationService;
  let product:Product;
  let photo :Photo;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [PostItemComponent],
    }).compileComponents();
    productService = TestBed.get(ProductService);
    authenticationService = TestBed.get(AuthenticationService);

    spyOn(productService, 'updateProduct').and.returnValue(of(product));
    spyOnProperty(authenticationService, 'currentUserValue', 'get').and.returnValue(1);
  });

  beforeEach(() => {
    product = new Product;
    photo = new Photo;
    fixture = TestBed.createComponent(PostItemComponent);
    component = fixture.componentInstance;
    component.productItem = {};
    component.productItem.photo = photo;
    fixture.detectChanges();
  });

  it('should create', () => {
     
    expect(component).toBeTruthy();
  });

  it('ngOnInit() should exists', () => {
   expect(component.ngOnInit).toBeTruthy();
 });

 it('openEdit() should exists', () => {
  expect(component.openEdit).toBeTruthy();
});

it('handleFileInput() should exists', () => {
  expect(component.handleFileInput).toBeTruthy();
});

it('checkSave() should exists', () => {
  expect(component.checkSave).toBeTruthy();
});

it('submit() should exists', () => {
  expect(component.submit).toBeTruthy();
});

it('openRemove() should exists', () => {
  expect(component.openRemove).toBeTruthy();
});

it('deletePost() should exists', () => {
  expect(component.deletePost).toBeTruthy();

});

it('submit() should call UserService to login', () => {
  product.title = "iphone5";
  product.category='Accessories';
  product.description='this is an iphone5';
  product.price=120;

  const title = component.form.controls.title;
  title.setValue(product.title);
  const category = component.form.controls.category;
  category.setValue(product.category);
  const description = component.form.controls.description;
  category.setValue(product.description);
  expect(component.checkSave).toBeFalse;
  component.submit();

});

});
