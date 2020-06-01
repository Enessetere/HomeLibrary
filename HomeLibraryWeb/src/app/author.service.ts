import { Injectable } from '@angular/core';
import {Author} from './author';
import {HttpClient, HttpClientModule, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {map, retry} from 'rxjs/operators';
import {log} from 'util';
import {AuthorModel} from './author-model';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  private apiUrl = `http://localhost:9081/api/authors`;

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
        'Content-Type': 'application/JSON'
    })
  };

  getData() {
    return this.http.get<AuthorModel>(this.apiUrl);
  }

}
