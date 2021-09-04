import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs/internal/observable/of';
import { Photo } from 'src/app/models/Photo';
import { Product } from 'src/app/models/Product';
import { AuthenticationService } from 'src/app/services/authentication.service';
import { ProductService } from 'src/app/services/product.service';
import { AccessoriesComponent } from '../accessories.component';

import { ProductItemAccessoriesComponent } from './product-item-accessories.component';

describe('ProductItemAccessoriesComponent', () => {
  let component: ProductItemAccessoriesComponent;
  let fixture: ComponentFixture<ProductItemAccessoriesComponent>;
  let productService: ProductService;
  let authenticationService: AuthenticationService;
  let product:Product;
  let photo :Photo;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [ProductItemAccessoriesComponent],
      
    })
      .compileComponents();
      productService = TestBed.get(ProductService);
    authenticationService = TestBed.get(AuthenticationService);
    spyOn(productService, 'updateProductWithoutImage').and.returnValue(of(product));
    spyOnProperty(authenticationService, 'currentUserValue', 'get').and.returnValue(1);
  });

  beforeEach(() => {
    product = new Product;
    photo = new Photo;
    fixture = TestBed.createComponent(ProductItemAccessoriesComponent);
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

  it('handleAddToCart() should exists', () => {
    expect(component.handleAddToCart).toBeTruthy();
  });

  it('toggleHidden() should exists', () => {
    expect(component.toggleHidden).toBeTruthy();
  });

  it('toggleHidden() should call ProductService', () => {
    component.toggleHidden(component.productItem);
    expect(productService.updateProductWithoutImage).toHaveBeenCalled();
  });

  it('toggleHidden() should check if productItem is Hidden', () => {
    component.productItem.hidden = false;
    component.toggleHidden(component.productItem);
    expect(component.isHidden).toBeFalse;
    expect(productService.updateProductWithoutImage).toHaveBeenCalled();
  });

});


