import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Email} from './email';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmailService {
  private apiUrl = `http://localhost:9081/api/contact`;

  constructor(private http: HttpClient) { }

  httpOptions = {
    headers: new HttpHeaders({
      'Content-Type': 'application/JSON'
    })
  };

  sendData(email: Email): Observable<Email> {
    return this.http.post<Email>(this.apiUrl, JSON.stringify(email), {
      headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
      }
    });
  }
}
