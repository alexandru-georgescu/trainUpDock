import { Component, OnInit, Input } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.css'],
})
export class LoginPageComponent implements OnInit {
  
  
  loginForm : FormGroup;
  user : User;
  currentUser : User;
  submitted = false;
  wrongInput = false;
  alreadyLoggedIn = false;
  loggedIn = false;


  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private router: Router
              ) {
    
   }

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      email: ['', Validators.required],
      password: ['', Validators.required]
      });
  }

  get controls() {
    return this.loginForm.controls;
  }

  onSubmit() {
    this.submitted = true;   
    if (this.controls.email.value != '' && this.controls.password.value != '') {
      this.login();
    } 
  }
  
  login() {
    let user = new User(this.controls.email.value,null, null, null, this.controls.password.value, [], 't.m@trainup.com', []);
    this.userService.login(user).subscribe(data => {
      this.user = data;
      if (this.user == null || this.user == undefined) {
        this.controls.email.setValue('');
        this.controls.password.setValue('');
        this.submitted = false;
        this.wrongInput = true;        
      } else {
        this.currentUser = JSON.parse(localStorage.getItem('currentUser'));
        if (this.currentUser != null && this.currentUser.email == this.user.email) {
          this.alreadyLoggedIn = true;
          this.loggedIn = true;
          localStorage.setItem('loggedIn','true');

        }
        else if (this.user.type === "PM"){
          this.loggedIn = true;
          localStorage.setItem('loggedIn','true');
          localStorage.setItem('currentUser', JSON.stringify(this.user));
          this.router.navigate(['/pm']);
        }
        else if (this.user.type === "USER") {
          this.loggedIn = true;
          localStorage.setItem('loggedIn','true');
          localStorage.setItem('currentUser', JSON.stringify(this.user));
          this.router.navigate(['/user']);
        }
        else if (this.user.type === "TM"){
          this.loggedIn = true;
          localStorage.setItem('loggedIn','true');
          localStorage.setItem('currentUser', JSON.stringify(this.user));
          this.router.navigate(['/tm']);
        }
      }
    });
  }
}