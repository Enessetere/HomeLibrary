import {Component, OnInit} from '@angular/core';
import {Author} from '../author';
import {AuthorService} from '../author.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-author-form',
  templateUrl: './author-form.component.html',
  styleUrls: ['./author-form.component.css']
})
export class AuthorFormComponent implements OnInit {

  constructor(private authorService: AuthorService, private router: Router) { }

  ngOnInit(): void {
  }

  onClickSubmit(authorForm) {
    const author: Author = new Author();
    author.firstName = authorForm.first_name;
    author.lastName = authorForm.last_name;
    this.authorService.sendData(author).subscribe();
    this.router.navigate(['authors/success']).then();
  }
}
