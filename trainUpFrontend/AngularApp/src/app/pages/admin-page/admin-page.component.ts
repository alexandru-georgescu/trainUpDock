import { Component, OnInit } from '@angular/core';
import { LoginPageComponent } from '../login-page/login-page.component';
import { Router } from '@angular/router';
import { User } from 'src/app/models/user';

@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {

  user : User;

  constructor(private loginPage: LoginPageComponent,
    private router: Router
  ) { }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
  }
  
  logout() {
    localStorage.removeItem('currentUser');
    this.loginPage.loggedIn = false;
    localStorage.setItem('loggedIn', 'false');
    this.loginPage.alreadyLoggedIn = false;
    this.router.navigate(['/login']);
  }
}
