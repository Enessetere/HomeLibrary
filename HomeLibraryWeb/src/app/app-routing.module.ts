import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {ContactUsComponent} from './contact-us/contact-us.component';
import {AuthorsComponent} from './authors/authors.component';
import {AuthorFormComponent} from './author-form/author-form.component';
import {AuthorComponent} from './author/author.component';
import {AuthorCreatedComponent} from './author-created/author-created.component';
import {BooksComponent} from './books/books.component';
import {BookComponent} from './book/book.component';
import {BookFormComponent} from './book-form/book-form.component';

const routes: Routes = [
  {path: ``, component: HomeComponent},
  {path: `contact`, component: ContactUsComponent},
  {path: `authors`, component: AuthorsComponent},
  {path: `create_author`, component: AuthorFormComponent},
  {path: 'authors/success', component: AuthorCreatedComponent},
  {path: 'authors/:idx', component: AuthorComponent},
  {path: `books`, component: BooksComponent},
  {path: `create_book`, component: BookFormComponent},
  {path: `books/:isbn`, component: BookComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const RoutingComponent = [HomeComponent, ContactUsComponent, AuthorsComponent, BooksComponent];
