export class Author {
  idx: bigint;
  firstName: string;
  lastName: string;
  books: string[] = [];

  convert(singleAuthorArray) {
    this.idx = singleAuthorArray[0];
    this.firstName = singleAuthorArray[1];
    this.lastName = singleAuthorArray[2];
    this.books = singleAuthorArray[3];
  }
}
