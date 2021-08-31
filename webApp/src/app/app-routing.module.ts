import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { LoginComponent } from './login/login.component';
import { LogoutComponent } from './logout/logout.component';
import { MypostsComponent } from './myposts/myposts.component';
import { MyprofileComponent } from './myprofile/myprofile.component';
import { AuthGuard } from './helpers/auth.guard'
import { SignupComponent } from './signup/signup.component';


const routes: Routes = [
  { path: '', component: DashboardComponent, canActivate:[AuthGuard] },
  { path: 'login', component: LoginComponent },
  { path: 'myprofile', component: MyprofileComponent, canActivate:[AuthGuard]},
  {path: 'myposts', component: MypostsComponent, canActivate:[AuthGuard]},
  { path: 'logout', component: LogoutComponent, canActivate:[AuthGuard] },
  { path: 'signup', component: SignupComponent },
  // otherwise redirect to home
  { path: '**', redirectTo: '' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }