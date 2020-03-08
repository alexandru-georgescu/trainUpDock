import { Component, OnInit } from '@angular/core';
import { Validators, FormBuilder, FormGroup } from '@angular/forms';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  forgotpasswordForm: FormGroup;
  submitted = false;
  user: User;
  inexistentUser = false;

  constructor(private formBuilder: FormBuilder,
    private userService: UserService,
    private router: Router,
    private toastr: ToastrService) { }

  ngOnInit() {
    this.forgotpasswordForm = this.formBuilder.group({
      email: ['', Validators.required]
    });
  }

  get controls() {
    return this.forgotpasswordForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    this.userService.getUser(this.controls.email.value).subscribe(data => {
      this.user = data;
      if (this.controls.email.value == '') {
        this.inexistentUser = false;
      }
      if (this.user == null) {
        this.inexistentUser = true;
        return;
      }
      this.userService.resetPassword(this.user).subscribe();
      this.inexistentUser = false;
      this.toastr.success("An email has been sent to your address", "Done!", {
        timeOut: 2000,
        positionClass: 'toast-bottom-right'
      });
      setTimeout(() => {
        this.router.navigate(['/login']);
      }, 2000);
    });
  }

}