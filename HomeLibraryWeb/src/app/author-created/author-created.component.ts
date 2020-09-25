import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {Author} from '../author';


@Component({
  selector: 'app-author-created',
  templateUrl: './author-created.component.html',
  styleUrls: ['./author-created.component.css']
})
export class AuthorCreatedComponent implements OnInit {

  counter = 5;
  author = new Author();
  timeout;

  constructor(private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    this.author = history.state.data;
    this.timeout = setTimeout(() => this.router.navigate(['authors']).then(), 5000);
    setInterval(() => this.counter--, 1000);
  }

  redirect() {
    clearTimeout(this.timeout);
    this.router.navigate(['authors']).then();
  }
}
