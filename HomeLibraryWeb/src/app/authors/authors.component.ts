import {Component, OnInit} from '@angular/core';
import {AuthorService} from '../author.service';
import {Author} from '../author';
import {Router} from '@angular/router';
import {StorageService} from '../storage.service';

@Component({
  selector: 'app-authors',
  templateUrl: './authors.component.html',
  styleUrls: ['./authors.component.css']
})
export class AuthorsComponent implements OnInit {

  tmp = [];
  authors: Author[] = [];
  results: Author[] = [];
  fnSortedAsc = false;
  lnSortedAsc = false;

  constructor(private authorService: AuthorService, private router: Router, private storageService: StorageService) {
  }

  ngOnInit() {
    this.authorService.getData().subscribe((data) => {
      this.tmp = Object.values(data);
      this.authors = this.tmp[0];
      this.results = this.authors;
      document.getElementById('filter_input').focus();
    });
  }

  event(author: Author) {
    this.storageService.setState(author);
    this.router.navigate(['authors', author.idx]).then();
  }

  filter(value: string) {
    if (value === null) {
      this.results = this.authors;
    } else {
      this.results = this.authors.filter(
        author => (author.firstName !== undefined && author.firstName.toLocaleLowerCase().match(value.toLocaleLowerCase()))
          || author.lastName.toLocaleLowerCase().match(value.toLocaleLowerCase()));
      this.lnSortedAsc = this.fnSortedAsc = false;
    }
  }

  fnSort() {
    this.results.sort((a, b) => this.sortResult(a.firstName, b.firstName, this.fnSortedAsc));
    this.fnSortedAsc = !this.fnSortedAsc;
    this.lnSortedAsc = false;
  }

  lnSort() {
    this.results.sort((a, b) => this.sortResult(a.lastName, b.lastName, this.lnSortedAsc));
    this.lnSortedAsc = !this.lnSortedAsc;
    this.fnSortedAsc = false;
  }

  sortResult(a: string, b: string, ascending: boolean): number {
    if (a === undefined) {
      return (ascending) ? 1 : -1;
    } else if (b === undefined) {
      return (ascending) ? -1 : 1;
    } else if (ascending) {
      return -a.localeCompare(b, undefined, {numeric: true});
    }
    return a.localeCompare(b, undefined, {numeric: true});
  }
}
