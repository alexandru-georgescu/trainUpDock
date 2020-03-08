import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree, CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  user: User;
  loggedIn: boolean;

  constructor(private router: Router) { }

  canActivate(next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
    if (localStorage.getItem('loggedIn') == 'true') {
      this.loggedIn = true;
    } else {
      this.loggedIn = false;
    }

    if (this.loggedIn === false) {
      return true;
    }
    this.user = JSON.parse(localStorage.getItem('currentUser'));
    if (this.user.type === 'PMTECH' || this.user.type === 'PMSOFT' || this.user.type === 'PMPROC') {
      this.router.navigate(['pm']);
    } else {
      this.router.navigate([this.user.type.toLowerCase()]);
    }
    return false;

  }

}
