import { browser, by, element, ElementFinder } from 'protractor';

export class AppPage {

   navigateTo() {
    return browser.get(browser.baseUrl) as Promise<any>;
  }


  navigateToSignUp() {
    return browser.get('/signUp');
  }

  getRouterOutlet(): ElementFinder {
    return element(by.tagName('router-outlet'));
  }}
