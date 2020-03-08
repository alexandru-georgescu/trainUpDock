import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { Sort } from '@angular/material/sort';
import { MatDialog } from '@angular/material';
import { AddCourseComponent } from './add-course/add-course.component';
import { LoginPageComponent } from '../login-page/login-page.component';
import { Router } from '@angular/router';
import { Course } from 'src/app/models/course';
import { CourseService } from 'src/app/services/course-service.service';
import { UserService } from 'src/app/services/user-service.service';
import { ToastrService } from 'ngx-toastr';
import { UserInfoComponent } from './user-info/user-info.component';
import { PmStatisticsComponent } from './pm-statistics/pm-statistics.component';
import { ShareService } from 'src/app/services/share.service';



@Component({
  selector: 'app-pm-page',
  templateUrl: './pm-page.component.html',
  styleUrls: ['./pm-page.component.css']
})
export class PmPageComponent implements OnInit {
  user: User;
  users: User[];
  usersList: User[][];
  courses: Course[];
  sortedData: User[][];
  selectedUser: User;
  full = false;
  searchText;
  searchUser;
  refusedUsers: User[][];
  acceptedUsers: User[][];
  markedCourses: Boolean[];
  emptyCourses: Boolean[];
  indexExpanded = -1;

  constructor(public dialog: MatDialog,
    private loginPage: LoginPageComponent,
    private router: Router,
    private courseService: CourseService,
    private userService: UserService,
    private toastr: ToastrService,
    private shareService: ShareService) { }

  sortData(sort: Sort) {
    this.usersList.forEach((el, index) => {
      const data = el;
      if (!sort.active || sort.direction === '') {
        this.sortedData[index] = data;
        return;
      }
      this.sortedData[index] = data.sort((a, b) => {
        const isAsc = sort.direction === 'asc';
        switch (sort.active) {
          case 'firstName': return compare(a.firstName, b.firstName, isAsc);
          case 'lastName': return compare(a.lastName, b.lastName, isAsc);
          case 'email': return compare(a.email, b.email, isAsc);
          default: return 0;
        }
      });
    })

  }

  acceptRejected(user : User, course : Course) {
    if (course.actualCapacity == 0) {
      this.doToastr(true, true);
      return;
    }

    this.userService.enrollRejected(user, course).subscribe(() => {
      this.courseService.getPmCourses(this.user).subscribe(data => {
        this.courses = data;
        let index = this.courses.findIndex(x => x.id === course.id);
        this.updateLists(course, index, true);
        this.doToastr(true, false);
      })
    })
  }

  rejectAccepted(user : User, course : Course) {
    this.userService.kickAccepted(user, course).subscribe(() => {
      this.courseService.getPmCourses(this.user).subscribe(data => {
        this.courses = data;
        let index = this.courses.findIndex(x => x.id === course.id);
        this.updateLists(course, index, true);
        this.doToastr(true, false);
      })
    })
  }

  yesClick(user: User, course: Course, printToast: Boolean): void {

    if (course.actualCapacity == 0) {
      this.doToastr(true, true);
      return;
    }

    this.userService.acceptCourse(user, course).subscribe(() => {
      this.courseService.getPmCourses(this.user).subscribe(data => {
        this.courses = data;
        let index = this.courses.findIndex(x => x.id === course.id);
        this.updateLists(course, index, true);
      },
        error => {
          this.doToastr(printToast, true);
        }
      );
    },
      error => {
        this.doToastr(printToast, true);
      },
      () => {
        this.doToastr(printToast, false);
      })
  }

  allYesClick(course: Course): void {

    if (course.actualCapacity == 0) {
      this.doToastr(true, true);
      return;
    }

    this.userService.getWaitUserCourses(course).subscribe(data => {
      let users = data;
      if (users.length > course.actualCapacity) {
        this.doToastr(true, true);
        return;
      }

      this.userService.acceptAll(users, course).subscribe(() => {
        this.courseService.getPmCourses(this.user).subscribe(data => {
          this.courses = data;
          let index = this.courses.findIndex(x => x.id === course.id);
          this.updateLists(course, index, true);
        })
      },
      () => {
        this.doToastr(true, false);
      });

    });
  }

  allNoClick(course : Course) {
    this.userService.getWaitUserCourses(course).subscribe(data => {
      let users = data;
      users.forEach(user => {
        this.noClick(user, course, false);
      });

      this.doToastr(true, false);
    })
  }


  noClick(user: User, course: Course, printToast: Boolean): void {

    this.userService.denyCourse(user, course).subscribe(() => {
      this.courseService.getPmCourses(this.user).subscribe(data => {
        this.courses = data;
        let index = this.courses.findIndex(x => x.id === course.id);
        this.updateLists(course, index, true);
      },
        error => {
          this.doToastr(printToast, true);
        }
      );
    },
      error => {
        this.doToastr(printToast, true);
      },
      () => {
        this.doToastr(printToast, false);
      })
  }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.courseService.getPmCourses(this.user).subscribe(data => {
      this.courses = data;
      this.usersList = new Array(this.courses.length);
      this.sortedData = new Array(this.courses.length);
      this.refusedUsers = new Array(this.courses.length);
      this.acceptedUsers = new Array(this.courses.length);
      this.markedCourses= new Array(this.courses.length);
      this.emptyCourses = new Array(this.courses.length);

      this.courses.forEach((course, index) => {
        this.usersList[index] = new Array();
        this.sortedData[index] = new Array();
        this.refusedUsers[index] = new Array();
        this.acceptedUsers[index] = new Array();
        this.updateLists(course, index, false);
      });
    });
  }

  updateLists(course : Course, index : number, indexEx : Boolean) {
    this.courseService.getRejectedUsers(course).subscribe(data => {
      this.refusedUsers[index] = data;
      this.courseService.getAcceptedUsers(course).subscribe(data => {
        this.acceptedUsers[index] = data;
        this.userService.getWaitUserCourses(course).subscribe(result => {
          this.usersList[index] = result;
          if (this.usersList[index].length === 0)
            this.markedCourses[index] = false;
          else 
            this.markedCourses[index] = true;
          if (this.usersList[index].length === 0 && this.refusedUsers[index].length === 0 && this.acceptedUsers[index].length === 0)
            this.emptyCourses[index] = true;
          else
            this.emptyCourses[index] = false;
          this.sortedData[index] = this.usersList[index].slice();
          if (indexEx === true) {
            this.indexExpanded = index;
          }
        })
      })
    })
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AddCourseComponent, {
      width: '560px',
      height: '400px',
    });

    dialogRef.afterClosed().subscribe(result => {
      this.ngOnInit();
    });
  }

  courseSort(event: any, mode: String) {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.courseService.getPmCourses(this.user).subscribe(data => {
      this.courses = data;
      if (mode === 'mod1')
        this.courses.sort(compareByNameAsc);
      if (mode === 'mod2')
        this.courses.sort(compareByCapacityAsc);
      if (mode === 'mod3')
        this.courses.sort(compareByStartDateAsc);
      if (mode === 'mod4')
        this.courses.sort(compareByStartDateDesc);
      if (mode === 'mod5')
        this.courses.sort(compareByNameDesc);
      if (mode === 'mod6')
        this.courses.sort(compareByCapacityDesc);
      this.usersList = new Array(this.courses.length);
      this.sortedData = new Array(this.courses.length);

      this.courses.forEach((course, index) => {
        this.usersList[index] = new Array();
        this.sortedData[index] = new Array();
        this.updateLists(course, index, false);
        this.indexExpanded = -1;
      });
    });
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.loginPage.loggedIn = false;
    localStorage.setItem('loggedIn', 'false');
    this.loginPage.alreadyLoggedIn = false;
    this.router.navigate(['/login']);
  }

  userInfo(user: User) {
    localStorage.setItem('selectedUser', JSON.stringify(user));
    const dialogRef = this.dialog.open(UserInfoComponent, {
      width: '560px',
      height: '280px',
    });
  }

  onStatistic() {
    const dialogRef = this.dialog.open(PmStatisticsComponent, {
      width: '700',
      height: '700'
    });
    this.userService.maxEnrollmentDomains(this.user).subscribe(data => {
      this.shareService.changeMaxDomains(data);
      this.userService.courseBelow50(this.user).subscribe(res => {
        this.shareService.changeBelow50(res);
      });
    });
  }

  doToastr(printToast : Boolean, error : Boolean) {
    if (printToast === false)
      return;
    else if (error === true) {
      this.toastr.error("Failed request", "Fail!", {
        timeOut: 3000,
        enableHtml: true,
        positionClass: 'toast-bottom-right'
      });
    }
    else {
      this.toastr.success("Your request has been sent", "Success!", {
        timeOut: 2000,
        enableHtml: true,
        positionClass: 'toast-bottom-right'
      });
    }
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}

function compareByNameAsc(a: Course, b: Course) {
  if (a.courseName < b.courseName) {
    return -1;
  }
  if (a.courseName > b.courseName) {
    return 1;
  }
}

function compareByNameDesc(a: Course, b: Course) {
  if (a.courseName < b.courseName) {
    return 1;
  }
  if (a.courseName > b.courseName) {
    return -1;
  }
}

function compareByCapacityAsc(a: Course, b: Course) {
  if (a.actualCapacity < b.actualCapacity) {
    return -1;
  }
  if (a.actualCapacity > b.actualCapacity) {
    return 1;
  }
}

function compareByCapacityDesc(a: Course, b: Course) {
  if (a.actualCapacity < b.actualCapacity) {
    return 1;
  }
  if (a.actualCapacity > b.actualCapacity) {
    return -1;
  }
}

function compareByStartDateAsc(a: Course, b: Course) {
  if (a.startDate < b.startDate) {
    return -1;
  }
  if (a.startDate > b.startDate) {
    return 1;
  }
}

function compareByStartDateDesc(a: Course, b: Course) {
  if (a.startDate < b.startDate) {
    return 1;
  }
  if (a.startDate > b.startDate) {
    return -1;
  }
}