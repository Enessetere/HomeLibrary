import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {AuthorService} from '../author.service';
import {Author} from '../author';
import {StorageService} from '../storage.service';
import {DisplayService} from '../display.service';

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
    private storageService: StorageService,
    private displayService: DisplayService) {
  }

  ngOnInit(): void {
    if (this.storageService.getState() === undefined) {
      let idx = 1;
      this.route.params.subscribe(params => idx = params.idx);
      this.service.getSingleRecord(idx).subscribe(
        data => {
          this.author.convert(Object.values(data));
        },
        err => console.log(err),
        () => {
          this.fillDescription();
          this.fillPath();
        }
      );
    } else {
      this.author = this.storageService.getState();
      this.storageService.setState(undefined);
      this.fillDescription();
      this.fillPath();
    }
  }

  fillDescription() {
    document.getElementById('author_description_text').innerHTML = this.author.description.replace(/\\n/gi, '<br/>');
  }

  fillPath() {
    document.getElementById('author_path').innerHTML =
      this.displayService.changeDisplay(this.author.firstName) + ' ' + this.displayService.changeDisplay(this.author.lastName);
  }

  redirect() {
    this.router.navigate(['authors']).then();
  }

  event(book: string) {
    this.router.navigate(['books', book]).then();
  }

  isEmpty(): boolean {
    return false;
  }
}
