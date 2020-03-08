import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material';
import { User } from 'src/app/models/user';
import { Sort } from '@angular/material/sort';
import { UserService } from 'src/app/services/user-service.service';
import { LoginPageComponent } from '../login-page/login-page.component';
import { Router } from '@angular/router';
import { Course } from 'src/app/models/course';
import { ToastrService } from 'ngx-toastr';
import { ModalComponent } from './modal/modal.component';
import { ShareService } from 'src/app/services/share.service';

@Component({
  selector: 'app-tm-page',
  templateUrl: './tm-page.component.html',
  styleUrls: ['./tm-page.component.css']
})
export class TmPageComponent implements OnInit {

  users: User[];
  sortedData: User[];
  user: User;
  course: Course;
  sentUser: User; //userul primit ca raspuns de la backend

  constructor(public dialog: MatDialog,
    private userService: UserService,
    private router: Router,
    private loginPage: LoginPageComponent,
    private toastr: ToastrService,
    private shareService : ShareService
  ) { }

  sortData(sort: Sort) {
    const data = this.users.slice();
    if (!sort.active || sort.direction === '') {
      this.sortedData = data;
      return;
    }
    this.sortedData = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'firstName': return compare(a.firstName, b.firstName, isAsc);
        case 'lastName': return compare(a.lastName, b.lastName, isAsc);
        case 'email': return compare(a.email, b.email, isAsc);
        case 'ongoing_courses': return compare(a.courses.length, b.courses.length, isAsc);
        case 'rejected_courses': return compare(a.rejectedList.length, b.rejectedList.length, isAsc);
        default: return 0;
      }
    });
  }

  ngOnInit() {

    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.userService.getTMUsers(this.user.email).subscribe(data => {
      this.users = data,
        this.sortedData = this.users.slice();
    });
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.loginPage.loggedIn = false;
    localStorage.setItem('loggedIn', 'false');
    this.loginPage.alreadyLoggedIn = false;
    this.router.navigate(['/login']);
  }

  onStatistic() {
    const dialogRef = this.dialog.open(ModalComponent, {
      width: '100vw',
      height: '500'
    });
    this.userService.typeStatistic(this.user).subscribe(data => {
      this.shareService.changeTypeStatistic(data);
      this.userService.yearStatistic(this.user).subscribe(res => {
        this.shareService.changeYearStatistic(res);
      });
    });
  }

  yesClick(user: User, course: Course): void {
    this.userService.addWaitToEnroll(user, course).subscribe(data => {
      this.sentUser = data;
      this.userService.getTMUsers(this.user.email).subscribe(data => {
        this.users = data,
          this.sortedData = this.users.slice();
      },
        error => {
          this.toastr.error("Failed request", "Fail!", {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          })
        });
    },
      error => {
        this.toastr.error("Failed request", "Fail!", {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        })
      },
      () => this.toastr.success("Your request has been sent", "Success!", {
        timeOut: 2000,
        positionClass: 'toast-bottom-right'
      }));
  }

  noClick(user: User, course: Course): void {
    this.userService.refuseToEnroll(user, course).subscribe(data => {
      this.sentUser = data;
      this.userService.getTMUsers(this.user.email).subscribe(data => {
        this.users = data,
          this.sortedData = this.users.slice();
      },
        error => {
          this.toastr.error("Failed request", "Fail!", {
            timeOut: 3000,
            positionClass: 'toast-bottom-right'
          })
        });
    },
      error => {
        this.toastr.error("Failed request", "Fail!", {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        })
      },
      () => this.toastr.success("Your request has been sent", "Success!", {
        timeOut: 2000,
        positionClass: 'toast-bottom-right'
      }));
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}