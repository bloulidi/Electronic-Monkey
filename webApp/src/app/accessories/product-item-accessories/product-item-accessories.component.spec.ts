import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductItemAccessoriesComponent } from './product-item-accessories.component';

describe('ProductItemAccessoriesComponent', () => {
  let component: ProductItemAccessoriesComponent;
  let fixture: ComponentFixture<ProductItemAccessoriesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProductItemAccessoriesComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductItemAccessoriesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /* it('should create', () => {
     expect(component).toBeTruthy();
   });*/
});
