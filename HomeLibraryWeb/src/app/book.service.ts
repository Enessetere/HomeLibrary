import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Router} from '@angular/router';
import {BookModel} from './book-model';
import {Book} from './book';
import {Author} from './author';

@Injectable({
  providedIn: 'root'
})
export class BookService {

  private apiUrl = `http://localhost:9081/api/books`;
  private byGenreApiUrl = `http://localhost:9081/api/books/genre?genre=`;
  private byAuthorApiUrl = `http://localhost:9081/api/books/authors?authors=`;

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

  getByGenre(genre: string) {
    return this.http.get<BookModel>(this.byGenreApiUrl + genre);
  }

  getByAuthor(authors: Author[]) {
    let authorsList = '';
    authors.forEach(author => authorsList += author.idx + `-`);
    return this.http.get<BookModel>(this.byAuthorApiUrl + authorsList.slice(0, authorsList.length - 1));
  }
}
