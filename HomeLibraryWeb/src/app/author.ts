import {Book} from './book';

export class Author {
  idx: bigint;
  firstName: string;
  lastName: string;
  books: Book[] = [];
  description: string;

  convert(singleAuthorArray) {
    this.idx = singleAuthorArray[0];
    this.firstName = singleAuthorArray[1];
    this.lastName = singleAuthorArray[2];
    this.description = singleAuthorArray[3];
    this.books = singleAuthorArray[4];
  }
}
