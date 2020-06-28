import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {Author} from '../author';

@Component({
  selector: 'app-author-failure',
  templateUrl: './author-failure.component.html',
  styleUrls: ['./author-failure.component.css']
})
export class AuthorFailureComponent implements OnInit {

  author = new Author();

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.author = history.state.data;
    const error = history.state.error;
    console.log(error);
  }

  redirect() {
    this.router.navigate(['authors']).then();
  }
}
