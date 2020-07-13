import {Injectable} from '@angular/core';
import {Author} from './author';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthorModel} from './author-model';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthorService {
  private apiUrl = `http://localhost:9081/api/authors`;

  constructor(private http: HttpClient, private router: Router) {
  }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/JSON'
    })
  };

  getData() {
    return this.http.get<AuthorModel>(this.apiUrl);
  }

  getSingleRecord(idx: number) {
    return this.http.get<Author>(this.apiUrl + '/' + idx);
  }

  sendData(author: Author): Observable<Author> {
    return this.http.post<Author>(this.apiUrl, JSON.stringify(author), {
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    });
  }
}
