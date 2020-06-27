import {Component, OnInit} from '@angular/core';
import {AuthorService} from '../author.service';
import {Author} from '../author';
import {Router} from '@angular/router';

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {

  tmp = [];
  authors: Author[] = [];

  constructor(private authorService: AuthorService, private router: Router) { }

  ngOnInit() {
    this.authorService.getData().subscribe((data) => {
      this.tmp = Object.values(data);
      this.authors = this.tmp[0];
    });
  }

  event(author: Author) {
    this.router.navigate(['authors', author.idx]).then();
  }
}
