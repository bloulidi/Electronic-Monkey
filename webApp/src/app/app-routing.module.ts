import { OrderHistoryComponent } from './orderHistory/orderHistory.component';
import { CartComponent } from './cart/cart.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { MypostsComponent } from './myposts/myposts.component';
import { AuthGuard } from './helpers/auth.guard'
import { SignupComponent } from './signup/signup.component';
import { PhonesComponent } from './phones/phones.component';
import { ComputersComponent } from './computers/computers.component';
import { AccessoriesComponent } from './accessories/accessories.component';
import { MyprofileComponent } from './myprofile/myprofile.component';
import { PostComponent } from './post/post.component';


const routes: Routes = [
  { path: '', component: DashboardComponent, canActivate: [AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'myprofile', component: MyprofileComponent, canActivate: [AuthGuard] },
  { path: 'myposts', component: MypostsComponent, canActivate: [AuthGuard] },
  { path: 'logout', component: LogoutComponent, canActivate: [AuthGuard] },
  { path: 'signup', component: SignupComponent },
  { path: 'myprofile', component: MyprofileComponent},
  {path: 'myposts', component: MypostsComponent},
  { path: 'logout', component: LogoutComponent, canActivate:[AuthGuard] },
  { path: 'signup', component: SignupComponent },
  { path: 'orderHistory', component: OrderHistoryComponent },
  { path: 'cart', component: CartComponent, canActivate: [AuthGuard] },
  { path: 'phones', component: PhonesComponent, canActivate: [AuthGuard] },
  { path: 'computers', component: ComputersComponent, canActivate: [AuthGuard] },
  { path: 'accessories', component: AccessoriesComponent, canActivate: [AuthGuard] },
  { path: 'post', component: PostComponent, canActivate: [AuthGuard] },

  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }