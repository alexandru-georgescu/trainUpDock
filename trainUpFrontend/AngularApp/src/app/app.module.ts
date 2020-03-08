import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { UserService } from './services/user-service.service';
import { LoginPageComponent } from './pages/login-page/login-page.component'
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RegisterPageComponent } from './pages/register-page/register-page.component';
import { NotFoundPageComponent } from './pages/not-found-page/not-found-page.component';
import { UserPageComponent } from './pages/user-page/user-page.component';
import { RoleGuard } from './guards/role.guard';
import { PreviousCoursesComponent } from './pages/user-page/previous-courses/previous-courses.component';
import { CurrentCoursesComponent } from './pages/user-page/current-courses/current-courses.component';
import { NextCoursesComponent } from './pages/user-page/next-courses/next-courses.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule, MatNativeDateModule, MatDatepickerModule } from '@angular/material';
import { TmPageComponent } from './pages/tm-page/tm-page.component';
import { MatExpansionModule, MatInputModule, MatFormFieldModule, MatPaginatorModule, MatTableModule, MatSortModule} from '@angular/material';
import { MaterialModule } from 'src/material.module';
import { ModalComponent } from './pages/tm-page/modal/modal.component';
import { PmPageComponent } from './pages/pm-page/pm-page.component';
import { AddCourseComponent } from './pages/pm-page/add-course/add-course.component';
import { CourseService } from './services/course-service.service';
import { ToastrModule } from 'ngx-toastr';
import { AuthGuard } from './guards/auth.guard';
import { ForgotPasswordComponent } from './pages/login-page/forgot-password/forgot-password.component';
import { UserInfoComponent } from './pages/pm-page/user-info/user-info.component';
import { MatSelectModule } from '@angular/material/select';
import { Ng2SearchPipeModule } from 'ng2-search-filter';
import { AdminPageComponent } from './pages/admin-page/admin-page.component';
import { PmStatisticsComponent } from './pages/pm-page/pm-statistics/pm-statistics.component';
import { UserStatisticsComponent } from './pages/user-page/user-statistics/user-statistics.component';
import { PreventDoubleClickDirective } from './directives/prevent-double-click.directive';
import { FusionChartsModule } from 'angular-fusioncharts';
import * as FusionCharts from 'fusioncharts';
import * as Charts from 'fusioncharts/fusioncharts.charts';
import * as TimeSeries from 'fusioncharts/fusioncharts.timeseries';
import { SearchFilterPipe } from 'src/searchfilterpipe';
import { AdminUserComponent } from './pages/admin-page/admin-user/admin-user.component';
import { AdminCourseComponent } from './pages/admin-page/admin-course/admin-course.component';


FusionChartsModule.fcRoot(FusionCharts, Charts, TimeSeries);


@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    RegisterPageComponent,
    NotFoundPageComponent,
    UserPageComponent,
    PreviousCoursesComponent,
    CurrentCoursesComponent,
    NextCoursesComponent,
    TmPageComponent,
    ModalComponent,
    PmPageComponent,
    AddCourseComponent,
    ForgotPasswordComponent,
    UserInfoComponent,
    AdminPageComponent,
    PmStatisticsComponent,
    UserStatisticsComponent,
    PreventDoubleClickDirective,
    SearchFilterPipe,
    AdminUserComponent,
    AdminCourseComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatExpansionModule,
    MatInputModule,
    BrowserAnimationsModule,
    MaterialModule,
    MatFormFieldModule,
    MatPaginatorModule, 
    MatTableModule, 
    MatSortModule,
    MatNativeDateModule,
    MatDatepickerModule,
    MatSelectModule,
    Ng2SearchPipeModule,
    ToastrModule.forRoot(),
    FusionChartsModule


  ],
  providers: [RoleGuard, UserService, LoginPageComponent, CourseService, AuthGuard, PmPageComponent],
  bootstrap: [AppComponent],
  entryComponents: [ModalComponent, AddCourseComponent, UserInfoComponent, PmStatisticsComponent, UserStatisticsComponent]
})
export class AppModule { }
