import { browser, by, element, ElementFinder } from 'protractor';

export class AppPage {

   navigateTo() {
    return browser.get(browser.baseUrl) as Promise<any>;
  }

  navigateToLogin() {
    return browser.get('/login');
  }

  navigateToSignUp() {
    return browser.get('/signup');
  }

  getRouterOutlet(): ElementFinder {
    return element(by.tagName('router-outlet'));
  }}
