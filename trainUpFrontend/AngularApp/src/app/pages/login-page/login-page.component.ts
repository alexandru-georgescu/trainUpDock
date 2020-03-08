import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormControl, FormGroupDirective, NgForm } from '@angular/forms';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';
import { ErrorStateMatcher } from '@angular/material';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent implements OnInit {


  loginForm: FormGroup;
  user: User;
  currentUser: User;
  submitted = false;
  wrongInput = false;
  alreadyLoggedIn = false;
  loggedIn = false;
  needToactivateAccount = false;
  needToSetTeamLeader = false;

  constructor(private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router
  ) {

  }

  ngOnInit() {
    this.submitted = false;
    this.wrongInput = false;
    this.alreadyLoggedIn = false;
    this.loggedIn = false;
    this.needToactivateAccount = false;
    this.needToSetTeamLeader = false;
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
    });
    if (localStorage.getItem('loggedIn') == 'true') {
      this.user = JSON.parse(localStorage.getItem('currentUser'));
      this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
      this.loggedIn = Boolean(localStorage.getItem('loggedIn'));
    }
  }

  get controls() {
    return this.loginForm.controls;
  }

  beforeLogIn() {
    this.submitted = true;
    this.controls.email.markAsTouched();
    this.controls.password.markAsTouched();
    if (this.controls.email.value != '' && this.controls.password.value != '') {
      this.login();
    }
  }

  login() {
    let user = new User(this.controls.email.value, null, null, null, this.controls.password.value, [], null, [], [], []);
    this.userService.login(user).subscribe(data => {
      this.user = data;
      if (this.user == null || this.user == undefined) {
        this.controls.email.setValue('');
        this.controls.password.setValue('');
        this.submitted = false;
        this.wrongInput = true;
        this.alreadyLoggedIn = false;
        this.needToactivateAccount = false;
        this.needToSetTeamLeader = false;

      } else {
        if (this.user.leader == null) {
          this.needToSetTeamLeader = true;
          this.needToactivateAccount = !this.user.enable;
          this.wrongInput = false;
        } else {
          this.needToSetTeamLeader = false;
          this.needToactivateAccount = !this.user.enable;
          this.wrongInput = false;
          if (this.needToactivateAccount === false) {
            this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
            if (this.currentUser != null && this.currentUser.email == this.user.email && this.currentUser.password == this.user.password) {
              this.alreadyLoggedIn = true;
              this.loggedIn = true;
              localStorage.setItem('loggedIn', 'true');

            }
            else if (this.user.type === "PMTECH" || this.user.type === "PMSOFT" || this.user.type === "PMPROC") {
              this.loggedIn = true;
              localStorage.setItem('loggedIn', 'true');
              localStorage.setItem('currentUser', JSON.stringify(this.user));
              this.router.navigate(['/pm']);
            }
            else if (this.user.type === "USER") {
              this.loggedIn = true;
              localStorage.setItem('loggedIn', 'true');
              localStorage.setItem('currentUser', JSON.stringify(this.user));
              this.router.navigate(['/user/curr']);
            }
            else if (this.user.type === "TM") {
              this.loggedIn = true;
              localStorage.setItem('loggedIn', 'true');
              localStorage.setItem('currentUser', JSON.stringify(this.user));
              this.router.navigate(['/tm']);
            }
            else if (this.user.type === "ADMIN") {
              this.loggedIn = true;
              localStorage.setItem('loggedIn', 'true');
              localStorage.setItem('currentUser', JSON.stringify(this.user));
              this.router.navigate(['/admin']);
            }
          }
        }
      }
    }
    );
  }
}