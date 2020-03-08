import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { Sort } from '@angular/material/sort';
import { MatDialog } from '@angular/material';
import { AddCourseComponent } from './add-course/add-course.component';
import { Router } from '@angular/router';
import { LoginPageComponent } from '../login-page/login-page.component';


@Component({
  selector: 'app-pm-page',
  templateUrl: './pm-page.component.html',
  styleUrls: ['./pm-page.component.css']
})
export class PmPageComponent implements OnInit {
  user: User;
  users: User[];
  sortedData: User[];

  constructor(public dialog: MatDialog,
    private router: Router,
    private loginPage: LoginPageComponent) { }

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
        default: return 0;
      }
    });
  }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.users = new Array();
    let user1 = new User('a.b@trainup.com', 'USER', 'a', 'b', 'Alex1234', [], 't.m@trainup.com', []);
    let user2 = new User('c.d@trainup.com', 'USER', 'c', 'd', 'Alex1234', [], 't.m@trainup.com', []);

    this.users.push(user1);
    this.users.push(user2);
    this.sortedData = this.users.slice();
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(AddCourseComponent, {
      width: '800px',
      height: '300px',
    });

    dialogRef.afterClosed().subscribe(result => {
    });
  }

  onSubmit() {
    this.logout();
  }

  logout() {
    localStorage.removeItem('currentUser');
    this.loginPage.loggedIn = false;
    localStorage.setItem('loggedIn', 'false');
    this.loginPage.alreadyLoggedIn = false;
    this.router.navigate(['/login']);
  }


}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}