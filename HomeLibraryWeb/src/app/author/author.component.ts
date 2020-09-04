import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthorService} from '../author.service';
import {Author} from '../author';
import {StorageService} from '../storage.service';

@Component({
  selector: 'app-author',
  templateUrl: './author.component.html',
  styleUrls: ['./author.component.css']
})
export class AuthorComponent implements OnInit {

  author = new Author();

  constructor(
    private route: ActivatedRoute,
    private service: AuthorService,
    private router: Router,
    private storageService: StorageService) {
  }

  ngOnInit(): void {
    let idx = 1;
    this.route.params.subscribe(params => idx = params.idx);
    if (this.storageService.getState() === undefined) {
      this.service.getSingleRecord(idx).subscribe(data => this.author.convert(Object.values(data)));
    } else {
      this.author = this.storageService.getState();
      this.storageService.setState(undefined);
    }
  }

  redirect() {
    this.router.navigate(['authors']).then();
  }

  event(book: string) {
    console.log(book);
  }

  isEmpty(): boolean {
    console.log(this.author.books);
    return false;
  }
}
