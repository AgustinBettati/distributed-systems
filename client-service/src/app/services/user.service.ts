import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {User} from '../models/User';

const url = 'http://localhost:8000/api/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) {
  }

  getUsers(): Observable<User[]> {
    return this.http.get<User[]>(url + 'user');
  }
}
