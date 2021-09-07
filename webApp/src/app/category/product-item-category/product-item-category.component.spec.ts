import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductItemCategoryComponent } from './product-item-category.component';

describe('ProductItemComputersComponent', () => {
  let component: ProductItemCategoryComponent;
  let fixture: ComponentFixture<ProductItemCategoryComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProductItemCategoryComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductItemCategoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /*it('should create', () => {
    expect(component).toBeTruthy();
  });*/
});
