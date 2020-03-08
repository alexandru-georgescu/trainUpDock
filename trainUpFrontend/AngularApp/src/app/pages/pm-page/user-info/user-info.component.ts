import { Component, OnInit } from '@angular/core';
import { MatDialogRef } from '@angular/material';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user-service.service';
import { PmPageComponent } from '../pm-page.component';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.css']
})
export class UserInfoComponent implements OnInit {

  user : User;
  userForm : FormGroup;

  constructor(
    public dialogRef: MatDialogRef<UserInfoComponent>,
    private userService: UserService,
  ) { }

  ngOnInit() {

    this.user = JSON.parse(localStorage.getItem('selectedUser'));
    
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
