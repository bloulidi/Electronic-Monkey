import { Component } from '@angular/core';
import { MatIconRegistry } from '@angular/material/icon';
import { DomSanitizer } from '@angular/platform-browser';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css'],
})
export class AppComponent {
  title = 'Electronic Monkey';
  constructor(
    private matIconRegistry: MatIconRegistry,
    private domSanitizer: DomSanitizer
  ) {
    this.matIconRegistry.addSvgIcon(
      'profil_icon',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/Profile_icon.svg'
      )
    );

    this.matIconRegistry.addSvgIcon(
      'cart_icon',
      this.domSanitizer.bypassSecurityTrustResourceUrl(
        '../assets/Cart_icon.svg'
      )
    );
  }
}
