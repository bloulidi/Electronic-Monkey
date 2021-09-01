import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs/internal/observable/of';
import { Product } from '../models/Product';
import { AuthenticationService } from '../services/authentication.service';
import { ProductService } from '../services/product.service';

import { PhonesComponent } from './phones.component';

describe('PhonesComponent', () => {
  let component: PhonesComponent;
  let fixture: ComponentFixture<PhonesComponent>;
  let productService: ProductService;
  let authenticationService: AuthenticationService;
  let productList: Product[];
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ ReactiveFormsModule, HttpClientTestingModule, RouterTestingModule],
      declarations: [ PhonesComponent ]
    })
    .compileComponents();
    productService = TestBed.get(ProductService);
    authenticationService = TestBed.get(AuthenticationService);
    spyOn(productService, 'getProductsByCategory').and.returnValue(of(productList));
    spyOnProperty(authenticationService, 'currentUserValue', 'get').and.returnValue(1);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PhonesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('ngOnInit() should exists', () => {
    expect(component.ngOnInit).toBeTruthy();
  });

  it('LoadProducts() should exists', () => {
    expect(component.loadProducts).toBeTruthy();
  });

  it('LoadProducts() should call ProductService', () => {

    const productList: Product[] = component.productList;
    component.loadProducts();
    expect(productService.getProductsByCategory).toHaveBeenCalled();
  });
});
