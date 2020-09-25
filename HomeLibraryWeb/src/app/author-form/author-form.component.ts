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

  onClickSubmit(authorForm: Author) {
    if (authorForm.firstName === '') {
      authorForm.firstName = undefined;
    }
    authorForm.description.replace('\n', '\\n');
    console.log(authorForm);
    this.authorService.sendData(authorForm).subscribe(
      createdAuthor => this.router.navigate(['authors/success'], {state: {data: createdAuthor}}),
      err => this.error = err.error
    );
  }

  resize() {
    const area = document.getElementById('description');
    setTimeout(() => {
      area.style.cssText = 'height:auto;';
      area.style.cssText = 'height:' + area.scrollHeight + 'px;';
    }, 0);
  }
}
