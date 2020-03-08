import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginPageComponent } from './pages/login-page/login-page.component';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { UserPageComponent } from './pages/user-page/user-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { RoleGuard } from './guards/role.guard';
import { PreviousCoursesComponent } from './pages/user-page/previous-courses/previous-courses.component';
import { CurrentCoursesComponent } from './pages/user-page/current-courses/current-courses.component';
import { NextCoursesComponent } from './pages/user-page/next-courses/next-courses.component';
import { TmPageComponent } from './pages/tm-page/tm-page.component';
import { PmPageComponent } from './pages/pm-page/pm-page.component';
import { AuthGuard } from './guards/auth.guard';
import { ForgotPasswordComponent } from './pages/login-page/forgot-password/forgot-password.component';
import { AdminPageComponent } from './pages/admin-page/admin-page.component';


const routes: Routes = [
  { path: '', redirectTo: '/login', pathMatch: 'full'},
  { path: 'login',canActivate: [AuthGuard], component: LoginPageComponent},
  { path: 'register', canActivate: [AuthGuard], component: RegisterPageComponent},
  { path: 'forgot-password', canActivate: [AuthGuard], component: ForgotPasswordComponent },
  { path: 'tm', canActivate: [RoleGuard], component: TmPageComponent },
  { path: 'pm', canActivate: [RoleGuard], component: PmPageComponent },
  { path: 'user', canActivate: [RoleGuard], component: UserPageComponent, 
                  children: [{path: 'prev' , component: PreviousCoursesComponent},
                            {path: 'curr' , component: CurrentCoursesComponent},
                            {path: 'next' , component: NextCoursesComponent}]},
  { path: 'admin', canActivate: [RoleGuard], component: AdminPageComponent },
  { path: '**', component : NotFoundPageComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
