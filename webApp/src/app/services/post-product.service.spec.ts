/* tslint:disable:no-unused-variable */

import { TestBed, async, inject } from '@angular/core/testing';
import { PostProductService } from './post-product.service';

describe('Service: PostProduct', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PostProductService]
    });
  });

  it('should ...', inject([PostProductService], (service: PostProductService) => {
    expect(service).toBeTruthy();
  }));
});
