/* tslint:disable:no-unused-variable */
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { SignupComponent } from './signup.component';

describe('SignupComponent', () => {
  let component: SignupComponent;
  let fixture: ComponentFixture<SignupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        HttpClientTestingModule,
        RouterTestingModule,
      ],
      declarations: [SignupComponent],
    }).compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SignupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('onSubmit() should exists', () => {
    expect(component.submit).toBeTruthy();
  });

  it('ngOnInit() should exists', () => {
    expect(component.ngOnInit).toBeTruthy();
  });

  /*it('onSubmit() should verify form has empty fields or not ', () => {
    component.form.controls.fullName = "Baddredine";
    component.submit();
    expect(component.message).toEqual('Email is required');
  });*/

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

  it('testing confirm password field validity', () => {
    const password = component.form.controls.password;
    password.setValue('Password1');
    const confirmPassword = component.form.controls.confirmPassword;
    confirmPassword.setValue('Password1');
    expect(confirmPassword.valid).toBeTruthy();
  });

  it('testing confirm password field not valid', () => {
    const password = component.form.controls.password;
    password.setValue('Password1');
    const confirmPassword = component.form.controls.confirmPassword;
    confirmPassword.setValue('Password123');
    expect(confirmPassword.valid).toBeFalsy();
  });
});
