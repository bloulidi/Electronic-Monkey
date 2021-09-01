import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input'
import { ReactiveFormsModule } from '@angular/forms';
import { MatCheckboxModule } from '@angular/material/checkbox';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { LoginComponent } from './login/login.component';

import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { SignupComponent } from './signup/signup.component';
import {MatButtonModule} from '@angular/material/button';
import {FormsModule} from '@angular/forms';
import { LogoutComponent } from './logout/logout.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { RouterModule } from '@angular/router';
import {MatIconModule} from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import { MatBadgeModule } from '@angular/material/badge';
import { MyprofileComponent } from './myprofile/myprofile.component';
import { MypostsComponent } from './myposts/myposts.component';
import { PostItemComponent } from './myposts/post-item/post-item.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component';
import { PostComponent } from './post/post.component';
import { MatDialogModule } from '@angular/material/dialog';
import { JwtInterceptor } from './helpers/jwt.interceptor';
import { ErrorInterceptor } from './helpers/error.interceptor';
import {MatSelectModule} from '@angular/material/select';
import { PhonesComponent } from './phones/phones.component';
import { ComputersComponent } from './computers/computers.component';
import { AccessoriesComponent } from './accessories/accessories.component';
import { ProductItemPhonesComponent } from './phones/product-item-phones/product-item-phones.component';
import { ProductItemComputersComponent } from './computers/product-item-computers/product-item-computers.component';
import { ProductItemAccessoriesComponent } from './accessories/product-item-accessories/product-item-accessories.component';

@NgModule({
  declarations: [	
    AppComponent,
    LoginComponent,
    SignupComponent,
    LogoutComponent,
    DashboardComponent,
    MyprofileComponent,
    MypostsComponent,
    PostItemComponent,
    FooterComponent,
    HeaderComponent,
    PostComponent,
    PhonesComponent,
    ComputersComponent,
    AccessoriesComponent,
    ProductItemPhonesComponent,
    ProductItemComputersComponent,
    ProductItemAccessoriesComponent
  ],
  
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatCheckboxModule,
    HttpClientModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
    MatCheckboxModule,
    HttpClientModule,
    FormsModule,
    MatIconModule,
    MatMenuModule,
    MatBadgeModule,
    MatDialogModule,
    MatIconModule,
    MatSelectModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: JwtInterceptor, multi: true },
    { provide: HTTP_INTERCEPTORS, useClass: ErrorInterceptor, multi: true },
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
