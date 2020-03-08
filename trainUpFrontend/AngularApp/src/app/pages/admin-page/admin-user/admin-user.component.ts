import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/models/user';
import { UserService } from 'src/app/services/user-service.service';
import { ToastrService } from 'ngx-toastr';
import { Sort } from '@angular/material/sort';

@Component({
  selector: 'app-admin-user',
  templateUrl: './admin-user.component.html',
  styleUrls: ['./admin-user.component.css']
})
export class AdminUserComponent implements OnInit {

  user: User;
  users: User[];
  sortedDataUser: User[];
  defaultUsers: User[];
  types: String[];
  enables: boolean[];
  leaders: string[];
  selectedUserType: String[];
  selectedUserEnable: boolean[];
  selectedUserLeader: String[];
  projectManagers : string[];


  constructor(    private userService: UserService,
    private toastr: ToastrService,
    ) {
    this.types = ['TM', 'PMTECH', 'PMSOFT', 'PMPROC', 'USER'];
    this.enables = [false, true];
   }

  ngOnInit() {
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    this.userService.usersFindAll().subscribe(users => {
      this.leaders = users.filter(u => u.type != 'USER').map(u => u.email);
      this.projectManagers = users.filter(u => u.type === 'PMSOFT' || u.type == 'PMTECH' || u.type == 'PMPROC').map(u => u.email);

      this.users = users.slice(1, users.length);
      this.sortedDataUser = this.users;
      this.updateUserData();
      if (this.defaultUsers === undefined) {
        this.defaultUsers = JSON.parse(JSON.stringify(this.users));
      }
    });
  }

  sortDataUser(sort: Sort) {
    const data = this.users;
    if (!sort.active || sort.direction === '') {
      this.sortedDataUser = data;
      return;
    }
    this.sortedDataUser = data.sort((a, b) => {
      const isAsc = sort.direction === 'asc';
      switch (sort.active) {
        case 'id': return compare(a.id, b.id, isAsc);
        case 'email': return compare(a.email, b.email, isAsc);
        case 'type': return compare(a.type, b.type, isAsc);
        case 'firstName': return compare(a.firstName, b.firstName, isAsc);
        case 'lastName': return compare(a.lastName, b.lastName, isAsc);
        case 'enable': return compare(a.enable.toString(), b.enable.toString(), isAsc);
        case 'leader': return compare(a.leader, b.leader, isAsc);
        default: return 0;
      }
    });
    this.updateUserData();
  }


  updateUserData() {
    this.selectedUserType = this.sortedDataUser.map(u => u.type);
    this.selectedUserEnable = this.sortedDataUser.map(u => u.enable);
    this.selectedUserLeader = this.sortedDataUser.map(u => u.leader);
  }

  resetChangesUser() {
    this.users = this.defaultUsers;
    this.ngOnInit();
  }

  saveChangesUser() {
    if (confirm("This changes can't be undone. Are you sure?")){
    this.defaultUsers = JSON.parse(JSON.stringify(this.users));
    this.userService.updateUsers(this.users).subscribe(a => {this.ngOnInit()},
    error => {
      this.toastr.error("Failed request", "Fail!", {
        timeOut: 3000,
        positionClass: 'toast-bottom-right'
      })
    },
    () => this.toastr.success("Data has been saved", "Success!", {
      timeOut: 2000,
      positionClass: 'toast-bottom-right'
    }));
  } 
  }

  changeType(index: number, newType: string) {
    this.sortedDataUser[index].type = newType;
    this.users = this.sortedDataUser;
  }

  changeEnable(index: number, newEnable: boolean) {
    this.sortedDataUser[index].enable = newEnable;
    this.users = this.sortedDataUser;
  }

  changeLeader(index: number, newLeader: string) {
    this.sortedDataUser[index].leader = newLeader;
    this.users = this.sortedDataUser;
  }
}

function compare(a: number | string, b: number | string, isAsc: boolean) {
  return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
}
