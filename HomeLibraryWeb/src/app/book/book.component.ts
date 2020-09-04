import { Component, OnInit } from '@angular/core';
import {Book} from '../book';
import {ActivatedRoute, Router} from '@angular/router';
import {BookService} from '../book.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  book = new Book();

  constructor(private route: ActivatedRoute, private service: BookService, private router: Router) { }

  ngOnInit(): void {
    let isbn = '';
    this.route.params.subscribe(params => isbn = params.isbn);
    this.service.getSingleRecord(isbn).subscribe(data => this.book.convert(Object.values(data)));
  }

}
