import { browser, by, element, logging } from 'protractor';
import { AppPage } from './app.po';


describe('workspace-project App', () => {
  let page: AppPage;

  beforeEach(() => {
    page = new AppPage();
  });

    it('should check login form if email is empty', async () => {
      page.navigateTo();
      element(by.css('input[type="text"]')).sendKeys();
      element(by.css('.btn')).click();
      expect(element(by.css('.alert')).getText()).toContain("Email and Password should not be empty!!! Please verify details");
    });

    it('should check login form if password is empty', async () => {
      page.navigateTo();
      element(by.css('input[type="password"]')).sendKeys();
      element(by.css('.btn')).click();
      expect(element(by.css('.alert')).getText()).toContain("Email and Password should not be empty!!! Please verify details");
    });

    it('should check login form is valid or not given valid email to form and empty password', async () => {
      page.navigateTo();
      element(by.css('input[type="text"]')).sendKeys('soukaina@cgi.com');
      element(by.css('.btn')).click();
      expect(element(by.css('.alert')).getText()).toContain("Email and Password should not be empty!!! Please verify details");
    });

    it('should check login form is valid or not given valid password to form and empty email', async () => {
      page.navigateTo();
      element(by.css('input[type="password"]')).sendKeys('admin');
      element(by.css('.btn')).click();
      expect(element(by.css('.alert')).getText()).toContain("Email and Password should not be empty!!! Please verify details");
    });

    it('should check login form is valid or not given valid password to form and invalid email', async () => {
      page.navigateTo();
      element(by.css('input[type="text"]')).sendKeys('admin@cgi.com');
      element(by.css('input[type="password"]')).sendKeys('admin');
      element(by.css('.btn')).click();
      expect(element(by.css('.alert')).getText()).toContain("Email is not valid !");
    });
    
  afterEach(async () => {
    // Assert that there are no errors emitted from the browser
    const logs = await browser.manage().logs().get(logging.Type.BROWSER);
    expect(logs).not.toContain(jasmine.objectContaining({
      level: logging.Level.SEVERE,
    } as logging.Entry));
  });
});
