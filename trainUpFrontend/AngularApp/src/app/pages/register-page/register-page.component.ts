import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register-page',
  templateUrl: './register-page.component.html',
  styleUrls: ['./register-page.component.css']
})
export class RegisterPageComponent implements OnInit {

  registerForm : FormGroup;
  user : User;
  submitted = false;
  alreadyRegistered = false;

  constructor(private formBuilder: FormBuilder,
              private userService: UserService,
              private router: Router) {
    
   }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      password: ['', Validators.required],
      cPassword: ['', Validators.required]
      });
  }

  get controls() {
    return this.registerForm.controls;
}

  onSubmit() {
    this.submitted = true;

    if (this.registerForm.invalid) { 
      return; 
    }

    let user = new User(this.controls.email.value, 'USER', this.controls.firstName.value, this.controls.lastName.value, this.controls.password.value, [], 't.m@trainup.com', []);
    this.userService.register(user).subscribe(data => {
       this.user = data;
       if (this.user == null) {
          this.alreadyRegistered = true;
          return;
       }
       this.router.navigate(['/login']);
     });
    
  }

}