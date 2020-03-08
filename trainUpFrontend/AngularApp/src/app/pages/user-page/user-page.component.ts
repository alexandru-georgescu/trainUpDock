import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { Router } from '@angular/router';
import { LoginPageComponent } from '../login-page/login-page.component';
import { ShareService } from 'src/app/services/share.service';
import { MatDialog } from '@angular/material';
import { CurrentCoursesComponent } from './current-courses/current-courses.component';
import { UserStatisticsComponent } from './user-statistics/user-statistics.component';
import { UserService } from 'src/app/services/user-service.service';


@Component({
  selector: 'app-user-page',
  templateUrl: './user-page.component.html',
  styleUrls: ['./user-page.component.css'],
})
export class UserPageComponent implements OnInit {


  user: User;

  constructor(private router: Router,
    private loginPage: LoginPageComponent,
    private shareService: ShareService,
    private userService: UserService,
    private dialog: MatDialog
  ) {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.shareService.changeMessage(localStorage.getItem('currentUser'));
  }

  ngOnInit() {
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.loginPage.loggedIn = false;
    localStorage.setItem('loggedIn', 'false');
    this.loginPage.alreadyLoggedIn = false;
    this.router.navigate(['/login']);
  }

  onStatistic() {
    const dialogRef = this.dialog.open(UserStatisticsComponent, {
      width: '700',
      height: '700'
    });
    this.userService.attendedDays(this.user).subscribe(data => {
      this.shareService.changeAttendedDays(data);
    });
    this.userService.upcomingDays(this.user).subscribe(data => {
      this.shareService.changeUpcomingDays(data);
    });
    this.userService.courseStatistic(this.user).subscribe(data => {
      this.shareService.changeCourseStatistic(data);
    })
  }
}