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

  error = null;

  constructor(private authorService: AuthorService, private router: Router) {
  }

  ngOnInit(): void {
  }

  onClickSubmit(authorForm) {
    const author: Author = new Author();
    if (authorForm.first_name === '') {
      author.firstName = null;
    } else {
      author.firstName = authorForm.first_name;
    }
    author.lastName = authorForm.last_name;
    if (authorForm.author_description === '') {
      author.description = null;
    } else {
      author.description = authorForm.author_description;
    }
    console.log(author);
    this.authorService.sendData(author).subscribe(
      createdAuthor => this.router.navigate(['authors/success'], {state: {data: createdAuthor}}),
      err => this.error = err.error
    );
  }

  onClickError(errors) {
    console.log(errors);
  }

  resize() {
    const area = document.getElementById('description');
    setTimeout(() => {
      area.style.cssText = 'height:auto;';
      area.style.cssText = 'height:' + area.scrollHeight + 'px;';
    }, 0);
  }
}
