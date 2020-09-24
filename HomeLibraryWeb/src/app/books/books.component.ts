import { Component, OnInit } from '@angular/core';
import {BookService} from '../book.service';
import {Router} from '@angular/router';
import {Book} from '../book';
import {Author} from '../author';
import {StorageService} from '../storage.service';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {

  constructor(private bookService: BookService, private router: Router, private storage: StorageService) { }

  tmp = [];
  books: Book[] = [];

  ngOnInit(): void {
    this.bookService.getData().subscribe((data) => {
      this.tmp = Object.values(data);
      this.books = this.tmp[0];
    });
  }

  event(book) {
    this.storage.setState(book);
    this.router.navigate(['books', book.isbn]).then();
  }

  showAuthor(author: Author) {
    this.router.navigate(['authors', author.idx]).then();
  }
}
