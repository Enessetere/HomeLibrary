import {Component, OnInit} from '@angular/core';
import {Book} from '../book';
import {ActivatedRoute, Router} from '@angular/router';
import {BookService} from '../book.service';
import {StorageService} from '../storage.service';
import {DisplayService} from '../display.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  book = new Book();
  byGenre: Book[];

  constructor(private route: ActivatedRoute,
              private service: BookService,
              private router: Router,
              private storage: StorageService,
              private displayService: DisplayService) {
  }

  ngOnInit(): void {
    if (this.storage.getState() === undefined) {
      let isbn = '';
      this.route.params.subscribe(params => isbn = params.isbn);
      this.service.getSingleRecord(isbn).subscribe(
        data => this.book.convert(Object.values(data)),
          err => err.log,
        () => {
          this.fillPath();
          this.fillDescription();
          this.moreByGenre();
        });
    } else {
      this.book = this.storage.getState();
      this.storage.setState(undefined);
      this.fillPath();
      this.fillDescription();
      this.moreByGenre();
    }
  }

  redirectToAuthor(idx: bigint) {
    this.router.navigate(['authors', idx]).then();
  }

  redirect() {
    this.router.navigate(['books']).then();
  }

  fillPath() {
    document.getElementById('book_path').innerHTML = this.displayService.changeDisplay(this.book.title);
  }

  fillDescription() {
    document.getElementById('book-description').innerHTML = this.book.description.replace(/\\n/g, '<br/>');
  }

  moreByGenre() {
    this.service.getByGenre(this.book.genre).subscribe(
      (data) => this.byGenre = Object.values(data)[0],
        error => console.log(error),
      () => this.byGenre = this.byGenre.filter(book => book.title !== this.book.title));
  }
}
