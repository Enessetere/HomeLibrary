import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthorService} from '../author.service';
import {Author} from '../author';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  author = new Author();

  constructor(private route: ActivatedRoute, private service: AuthorService, private router: Router) { }

  ngOnInit(): void {
    let idx = 1;
    this.route.params.subscribe(params => idx = params.idx);
    this.service.getSingleRecord(idx).subscribe(data => this.author.convert(Object.values(data)));
  }

  redirect() {
    this.router.navigate(['authors']).then();
  }

  event(book: string) {
    console.log(book);
  }

  isEmpty(): boolean {
    console.log(this.author.books);
    return false;
  }
}
