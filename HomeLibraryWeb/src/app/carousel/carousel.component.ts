import {Component, Input, OnInit} from '@angular/core';
import {Book} from '../book';

@Component({
  selector: 'app-carousel',
  templateUrl: './carousel.component.html',
  styleUrls: ['./carousel.component.css']
})
export class CarouselComponent implements OnInit {

  @Input() books: Book[];

  currentSlide = 0;

  constructor() { }

  ngOnInit(): void {
  }

  onPreviousClick() {
    const prev = this.currentSlide - 1;
    this.currentSlide = prev < 0 ? this.books.length - 1 : prev;
    document.getElementById('previous').blur();
  }

  onNextClick() {
    const next = this.currentSlide + 1;
    this.currentSlide = next === this.books.length ? 0 : next;
    document.getElementById('next').blur();
  }
}
