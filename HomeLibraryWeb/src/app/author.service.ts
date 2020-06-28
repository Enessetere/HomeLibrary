import {Injectable} from '@angular/core';
import {Author} from './author';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {AuthorModel} from './author-model';
import {catchError} from 'rxjs/operators';
import {Observable, throwError} from 'rxjs';

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

  getSingleRecord(idx: number) {
    return this.http.get<Author>(this.apiUrl + '/' + idx);
  }

  sendData(author: Author): Observable<Author> {
    return this.http.post<Author>(this.apiUrl, JSON.stringify(author), {headers: {'Content-Type': 'application/json', 'Access-Control-Allow-Origin': '*'}}).pipe(
      catchError(this.handleError)
    );
  }

  handleError(error){
    let errorMessage = '';
    if (error.error instanceof ErrorEvent) {
      errorMessage = `Error: ${error.error.message}`;
    } else {
      console.log(error);
      errorMessage = `Error code: ${error.status}\nMessage: ${error.message}`;
    }
    window.alert(errorMessage);
    return throwError(errorMessage);
  }
}
