import { Component, OnInit } from '@angular/core';
import {BookService} from '../book.service';
import {Router} from '@angular/router';
import {Book} from '../book';

@Component({
  selector: 'app-books',
  templateUrl: './books.component.html',
  styleUrls: ['./books.component.css']
})
export class BooksComponent implements OnInit {

  constructor(private bookService: BookService, private router: Router) { }

  tmp = [];
  books: Book[] = [];

  ngOnInit(): void {
    this.bookService.getData().subscribe((data) => {
      this.tmp = Object.values(data);
      this.books = this.tmp[0];
    });
  }

  event(isbn) {
    this.router.navigate(['books', isbn]).then();
  }
}
