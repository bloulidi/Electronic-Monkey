import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductItemPhonesComponent } from './product-item-phones.component';

describe('ProductItemPhonesComponent', () => {
  let component: ProductItemPhonesComponent;
  let fixture: ComponentFixture<ProductItemPhonesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProductItemPhonesComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductItemPhonesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /*it('should create', () => {
    expect(component).toBeTruthy();
  });*/
});