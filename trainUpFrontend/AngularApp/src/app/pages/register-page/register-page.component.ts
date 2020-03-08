import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';
import { LoginPageComponent } from '../login-page/login-page.component';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

  registerForm: FormGroup;
  user: User;
  submitted = false;
  alreadyRegistered = false;
  loggedIn = false;


  constructor(private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private loginPage: LoginPageComponent,
    private toastr: ToastrService) {

  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      cPassword: ['', Validators.required]
    });
    if (localStorage.getItem('loggedIn') == 'true') {
      this.user = JSON.parse(localStorage.getItem('currentUser'));
      this.loginPage.loggedIn = Boolean(localStorage.getItem('loggedIn'));
      this.loggedIn = Boolean(localStorage.getItem('loggedIn'));

    }
  }

  get controls() {
    return this.registerForm.controls;
  }

  register() {
    this.submitted = true;
    this.controls.email.markAsTouched();
    this.controls.firstName.markAsTouched();
    this.controls.lastName.markAsTouched();
    this.controls.password.markAsTouched();
    this.controls.cPassword.markAsTouched();

    if (this.registerForm.invalid) {
      return;
    }

    let user = new User(this.controls.email.value, 'USER', this.controls.firstName.value, this.controls.lastName.value, this.controls.password.value, [], null, [], [], []);
    this.userService.register(user).subscribe(data => {
      this.user = data;
      if (this.user == null) {
        this.alreadyRegistered = true;
        return;
      }
      this.toastr.success("You need to activate your account", "Registration done!", {
        timeOut: 10000,
        positionClass: 'toast-bottom-right'
      });
      this.router.navigate(['/login']);
    },
      error => {
        this.toastr.error("Failed request", "Fail!", {
          timeOut: 3000,
          positionClass: 'toast-bottom-right'
        });
      }
    )
  }
}