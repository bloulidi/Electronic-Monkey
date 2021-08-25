import { HttpClientTestingModule } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { AppRoutingModule } from '../app-routing.module';

import { AuthGuardService } from './auth-guard.service';
import { UserService } from './user.service';

describe('AuthGuardService', () => {
  let service: AuthGuardService;

  beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [ HttpClientTestingModule, AppRoutingModule],
      }).compileComponents();
    service = TestBed.inject(AuthGuardService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
