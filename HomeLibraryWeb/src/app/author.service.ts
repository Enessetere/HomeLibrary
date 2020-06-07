import {Injectable} from '@angular/core';
import {Author} from './author';
import {HttpClient, HttpHeaders} from '@angular/common/http';
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

  sendData(author: Author) {
    return this.http.post<Author>(this.apiUrl, JSON.stringify(author), {headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'}});
  }
}
