import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs/internal/observable/of';
import { UserService } from '../services/user.service';

import { MyprofileComponent } from './myprofile.component';

describe('MyprofileComponent', () => {
  let component: MyprofileComponent;
  let fixture: ComponentFixture<MyprofileComponent>;
  let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ HttpClientModule, ReactiveFormsModule, RouterTestingModule],
      declarations: [ MyprofileComponent ],
      providers: []
    })
    .compileComponents();
    userService = TestBed.get(UserService);
    spyOn(userService, 'getUserByEmail').and.returnValue(of(''));
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MyprofileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('ngOnInit() should exists', () => {
    expect(component.ngOnInit).toBeTruthy();
  });

  it('retreiveUserInfo() should exists', () => {
    expect(component.retreiveUserInfo).toBeTruthy();
  });

  it('onCancel() should exists', () => {
    expect(component.onCancel).toBeTruthy();
  });

  it('onSubmit() should exists', () => {
    expect(component.onSubmit).toBeTruthy();
  });


  
});
