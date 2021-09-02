import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductItemComputersComponent } from './product-item-computers.component';

describe('ProductItemComputersComponent', () => {
  let component: ProductItemComputersComponent;
  let fixture: ComponentFixture<ProductItemComputersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ProductItemComputersComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductItemComputersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  /*it('should create', () => {
    expect(component).toBeTruthy();
  });*/
});
