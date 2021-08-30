import { browser, by, element, logging } from 'protractor';
import { AppPage } from './app.po';


describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

  it('should load login component on base url', () => {
    browser.get('/login');
    expect<any>(browser.getCurrentUrl())
      .toEqual(browser.baseUrl + "login");
  });

  it('should load signUp component view on clicking SignUp and verify the url', () => {
    browser.get('/login');
    element(by.css('#btn_signUp')).click();
    expect<any>(browser.getCurrentUrl())
      .toEqual(browser.baseUrl + 'signup');
  });

  it('should contain <router-outlet>', () => {
    page.navigateTo();
    expect(page.getRouterOutlet).toBeTruthy('<router-outlet> should exist in app.component.html');
  });

  it('should check login form if email is empty', async () => {
    //browser.waitForAngularEnabled(false);
    page.navigateToLogin();
    element(by.css('input[type="email"]')).sendKeys();
    element(by.css('#btn_login')).click();
    expect<any>(element(by.css('.alert')).getText()).toEqual("All fields are required");
  });

  it('should check login form if password is empty', async () => {
    page.navigateToLogin();
    element(by.css('input[type="password"]')).sendKeys();
    element(by.css('#btn_login')).click();
    expect(element(by.css('.alert')).getText()).toContain("All fields are required");
  });

  it('should check login form is valid or not given valid email to form and empty password', async () => {
    page.navigateToLogin();
    element(by.css('input[type="email"]')).sendKeys('soukaina@cgi.com');
    element(by.css('#btn_login')).click();
    expect(element(by.css('.alert')).getText()).toContain("Password is required");
  });

  it('should check login form is valid or not given valid password to form and empty email', async () => {
    page.navigateToLogin();
    element(by.css('input[type="password"]')).sendKeys('admin');
    element(by.css('#btn_login')).click();
    expect(element(by.css('.alert')).getText()).toContain("Email is required");
  });

  it('should check login form is valid or not given valid password to form and invalid email', async () => {
    page.navigateToLogin();
    element(by.css('input[type="email"]')).sendKeys('admin');
    element(by.css('input[type="password"]')).sendKeys('admin');
    element(by.css('#btn_login')).click();
    expect(element(by.css('.alert')).getText()).toContain('Invalid email and/or password!');
  });

  it('should check sign up form is valid or not given empty email', async () => {
    page.navigateToSignUp();
    element(by.css('#fullNameField')).sendKeys('admin admin');
    element(by.css('#passwordField')).sendKeys('Password1');
    element(by.css('#confirmPasswordField')).sendKeys('Password1');
    element(by.buttonText('Sign Up')).click();
    expect(element(by.css('.alert')).getText()).toContain('Email is required');
  });

  it('should check sign up form is valid or not given empty full name', async () => {
    page.navigateToSignUp();
    element(by.css('#emailField')).sendKeys('admin@cgi.com');
    element(by.css('#passwordField')).sendKeys('Password1');
    element(by.css('#confirmPasswordField')).sendKeys('Password1');
    element(by.buttonText('Sign Up')).click();
    expect(element(by.css('.alert')).getText()).toContain("Full Name is required");
  });

  /*it('should check sign up form is valid or not given invalid password', async () => {
    page.navigateToSignUp();
    element(by.css('#fullNameField')).sendKeys('admin admin');
    element(by.css('#emailField')).sendKeys('admin@cgi.com');

    //element(by.css('#passwordField')).click();
    element(by.css('#passwordField')).sendKeys('Password');
    element(by.css('#passwordField')).isPresent;
    expect<any>(element(by.css('#passwordError')).getText()).toContain('Must contain at least 1 number!');
    //expect($('mat-error').isPresent()).toBe(true);
  });*/

  /*it('should check sign up form is valid or not given invalid confirm password field', async () => {
    page.navigateToSignUp();
    element(by.css('#fullNameField')).sendKeys('admin admin');
    element(by.css('#emailField')).sendKeys('admin@cgi.com');
    element(by.css('#passwordField')).sendKeys('Password1');
    element(by.css('#confirmPasswordField')).sendKeys('Password2');
    expect<any>(element(by.css('#confirmPasswordError')).getText()).toEqual('Password do not match');
  });*/

  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
