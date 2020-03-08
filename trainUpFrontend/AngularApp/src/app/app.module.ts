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
import { AuthGuard } from './auth.guard';
import { PreviousCoursesComponent } from './pages/user-page/previous-courses/previous-courses.component';
import { CurrentCoursesComponent } from './pages/user-page/current-courses/current-courses.component';
import { NextCoursesComponent } from './pages/user-page/next-courses/next-courses.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatMenuModule } from '@angular/material/menu';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material';
import { TmPageComponent } from './pages/tm-page/tm-page.component';
import { MatExpansionModule, MatInputModule, MatFormFieldModule, MatPaginatorModule, MatTableModule, MatSortModule} from '@angular/material';
import { MaterialModule } from 'src/material.module';
import { ModalComponent } from './pages/tm-page/modal/modal.component';
import { PmPageComponent } from './pages/pm-page/pm-page.component';
import { SlidePanelComponent } from './pages/pm-page/slide-panel/slide-panel.component';
import { AddCourseComponent } from './pages/pm-page/add-course/add-course.component';




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
    SlidePanelComponent,
    AddCourseComponent
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
    MatSortModule
  ],
  providers: [AuthGuard, UserService, LoginPageComponent],
  bootstrap: [AppComponent],
  entryComponents: [ModalComponent, AddCourseComponent]
})
export class AppModule { }
