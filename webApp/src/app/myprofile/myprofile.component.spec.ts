import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { of } from 'rxjs/internal/observable/of';
import { AuthenticationService } from '../services/authentication.service';
import { UserService } from '../services/user.service';

import { MyprofileComponent } from './myprofile.component';

describe('MyprofileComponent', () => {
  let component: MyprofileComponent;
  let fixture: ComponentFixture<MyprofileComponent>;
  let userService: UserService;
  let authenticationService: AuthenticationService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ HttpClientModule, ReactiveFormsModule, RouterTestingModule],
      declarations: [ MyprofileComponent ],
      providers: []
    })
    .compileComponents();
    userService = TestBed.get(UserService);
    authenticationService = TestBed.get(AuthenticationService);
    spyOn(userService, 'getUserByEmail').and.returnValue(of(''));
    spyOnProperty(authenticationService, 'currentUserValue', 'get').and.returnValue(1);
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

  it('retreiveUserInfo() should call UserService', () => {
    component.retreiveUserInfo();
    expect(userService.getUserByEmail).toHaveBeenCalled();
  });

  it('testing full name field validity', () => {
    const fullName = component.form.controls.fullName;
    fullName.setValue('testName');
    expect(fullName.valid).toBeTruthy();
  });

  it('testing email field validity', () => {
    const email = component.form.controls.email;
    email.setValue('testEmail@gmail.com');
    expect(email.valid).toBeTruthy();
  });

  it('testing email field not valid', () => {
    const email = component.form.controls.email;
    email.setValue('testEmailgmail.com');
    expect(email.valid).toBeFalsy();
  });

  it('testing password field not valid', () => {
    const password = component.form.controls.password;
    password.setValue('password');
    expect(password.valid).toBeFalsy();
  });

  it('testing password field validity', () => {
    const password = component.form.controls.password;
    password.setValue('Password1');
    expect(password.valid).toBeTruthy();
  });

});
