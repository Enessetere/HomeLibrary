import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {BookModel} from './book-model';
import {Book} from './book';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private apiUrl = `http://localhost:9081/api/books`;

  constructor(private http: HttpClient, private router: Router) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/JSON'
    })
  };

  getData() {
    return this.http.get<BookModel>(this.apiUrl);
  }

  getSingleRecord(isbn: string) {
    return this.http.get<Book>(this.apiUrl + `/` + isbn);
  }
}
