/* tslint:disable:no-unused-variable */

import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed, async, inject } from '@angular/core/testing';
import { PostProductService } from './post-product.service';

describe('Service: PostProduct', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ HttpClientTestingModule ],
      providers: [PostProductService]
    });
  });

  it('should ...', inject([PostProductService], (service: PostProductService) => {
    expect(service).toBeTruthy();
  }));
});
