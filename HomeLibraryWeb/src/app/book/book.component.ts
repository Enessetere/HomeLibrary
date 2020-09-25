import { Component, OnInit } from '@angular/core';
import {Book} from '../book';
import {ActivatedRoute, Router} from '@angular/router';
import {BookService} from '../book.service';
import {StorageService} from '../storage.service';

@Component({
  selector: 'app-book',
  templateUrl: './book.component.html',
  styleUrls: ['./book.component.css']
})
export class BookComponent implements OnInit {

  book = new Book();

  constructor(private route: ActivatedRoute, private service: BookService, private router: Router, private storage: StorageService) { }

  ngOnInit(): void {
    if (this.storage.getState() === undefined) {
      let isbn = '';
      this.route.params.subscribe(params => isbn = params.isbn);
      this.service.getSingleRecord(isbn).subscribe(data => this.book.convert(Object.values(data)));
    } else {
      this.book = this.storage.getState();
      this.storage.setState(undefined);
    }
  }

  redirectToAuthor(idx: bigint) {
    this.router.navigate(['authors', idx]).then();
  }
}
