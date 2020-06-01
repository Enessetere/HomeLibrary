import {Component, OnInit} from '@angular/core';
import {AuthorService} from '../author.service';
import {Author} from '../author';

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {

  tmp = [];
  authors: Author[] = [];

  constructor(private authorService: AuthorService) { }

  ngOnInit() {
    this.authorService.getData().subscribe((data) => {
      this.tmp = Object.values(data);
      this.authors = this.tmp[0];
    });
  }

}
