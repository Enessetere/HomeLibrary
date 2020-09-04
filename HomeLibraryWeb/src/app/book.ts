import {Author} from './author';

export class Book {
  isbn: string;
  title: string;
  authors: Author[];
  genre: string;
  count: number;
  description: string;

  convert(singleBookArray) {
    this.isbn = singleBookArray[0];
    this.title = singleBookArray[1];
    this.authors = singleBookArray[2];
    this.description = singleBookArray[3];
    this.genre = singleBookArray[4];
    this.count = singleBookArray[5];
  }
}
