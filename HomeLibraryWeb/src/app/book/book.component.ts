import {Component, OnInit} from '@angular/core';
import {Book} from '../book';
import {ActivatedRoute, Router} from '@angular/router';
import {BookService} from '../book.service';
import {StorageService} from '../storage.service';
import {DisplayService} from '../display.service';
import {range} from "rxjs";

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  book = new Book();
  byGenre: Book[];
  byAuthor: Book[];
  authors: string;

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
          this.fillData();
        });
    } else {
      this.book = this.storage.getState();
      this.storage.setState(undefined);
      this.fillData();
    }
  }

  private fillData() {
    this.fillPath();
    this.fillDescription();
    this.moreByGenre();
    this.moreByAuthor();
    this.refactorAuthorsList();
  }

  redirectToAuthor(idx: bigint) {
    this.router.navigate(['authors', idx]).then();
  }

  redirect() {
    this.router.navigate(['books']).then();
  }

  private fillPath() {
    document.getElementById('book_path').innerHTML = this.displayService.changeDisplay(this.book.title);
  }

  private fillDescription() {
    document.getElementById('book-description').innerHTML = this.book.description.replace(/\\n/g, '<br/>');
  }

  private moreByGenre() {
    this.service.getByGenre(this.book.genre).subscribe(
      (data) => this.byGenre = Object.values(data)[0],
        error => console.log(error),
      () => this.byGenre = this.byGenre.filter(book => book.title !== this.book.title));
  }

  private moreByAuthor() {
    this.service.getByAuthor(this.book.authors).subscribe(
      data => this.byAuthor = Object.values(data)[0],
      error => console.log(error),
      () => this.byAuthor = this.byAuthor.filter(book => this.book.title !== book.title)
    );
  }

  private refactorAuthorsList() {
    this.authors = '';
    for (const author of this.book.authors) {
      this.authors += ` ${author.firstName} ${author.lastName}`;
      if (author === this.book.authors[this.book.authors.length - 2]) {
        this.authors += ' or ';
      } else {
        this.authors += ',';
      }
    }
    this.authors = this.authors.slice(0, this.authors.length - 1);
  }
}
