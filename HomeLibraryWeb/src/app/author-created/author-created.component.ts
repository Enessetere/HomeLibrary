import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-author-created',
  templateUrl: './author-created.component.html',
  styleUrls: ['./author-created.component.css']
})
export class AuthorCreatedComponent implements OnInit {

  counter = 5;

  constructor(private router: Router, private route: ActivatedRoute) {
  }

  ngOnInit(): void {
    // this.route.params.subscribe(data => console.log(data));
    setTimeout(() => this.router.navigate(['authors']).then(), 5000);
    setInterval(() => this.counter--, 1000);
  }

  redirect() {
    this.router.navigate(['authors']).then();
  }
}
