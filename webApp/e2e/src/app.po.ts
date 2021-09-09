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

  navigateToDashboard() {
    return browser.get('');
  }

  navigateToMyProfile() {
    return browser.get('/myprofile');
  }

  navigateToMyPosts() {
    return browser.get('/myposts');
  }

  navigateToPhones() {
    return browser.get('/phones');
  }

  navigateToComputers() {
    return browser.get('/computers');
  }

  navigateToAccessories() {
    return browser.get('/accessories');
  }

  navigateToLogOut() {
    return browser.get('/logout');
  }

  getRouterOutlet(): ElementFinder {
    return element(by.tagName('router-outlet'));
  }
}
