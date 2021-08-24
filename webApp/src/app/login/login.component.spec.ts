import { HttpClientModule } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
//import { Router } from '@angular/router';
import { AppRoutingModule } from '../app-routing.module';

import { LoginComponent } from './login.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  //let userService: UserService;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ HttpClientModule, ReactiveFormsModule, RouterTestingModule],
      declarations: [ LoginComponent ],
      providers: []
    })
    .compileComponents();
    //userService = TestBed.get(UserService);
   //spyOn(userService, 'login').and.returnValue(of(''));
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  // test to check onSubmit method existence
  it('onSubmit() should exists', () => {
    expect(component.onSubmit).toBeTruthy();
  });

  // test to check ngOnInit method existence
  it('ngOnInit() should exists', () => {
    expect(component.ngOnInit).toBeTruthy();
  });

  // test to check clearForm method existence
  it('clearForm() should exists', () => {
    expect(component.clearForm).toBeTruthy();
  });

  // test to check onSubmit is verifying form is valid or not
  it('onSubmit() should verify form is valid or not ', () => {
    component.form.value.email = '';
    component.form.value.password = '';
    component.onSubmit();
    expect(component.message).toEqual('Email and Password should not be empty!!! Please verify details');
  });

  it('testing email field invalidity', () => {

    const email = component.form.controls.email;
    email.setValue('testing');

    const password = component.form.controls.password;
    password.setValue('admin');

    component.onSubmit();
    expect(component.message).toEqual('Invalid email and/or password!');
  });

  it('testing email field validity', () => {
    const email = component.form.controls.email;
    email.setValue('testing@cgi.com');
    expect(email.valid).toBeTruthy();
  });

  it('testing password field validity', () => {
    const password = component.form.controls.password;
    password.setValue('testing');
    expect(password.valid).toBeTruthy();
  });
});
