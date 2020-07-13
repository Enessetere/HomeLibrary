import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HomeComponent} from './home/home.component';
import {ContactUsComponent} from './contact-us/contact-us.component';
import {AuthorsComponent} from './authors/authors.component';
import {AppRoutingModule, RoutingComponent} from './app-routing.module';
import {HttpClientModule} from '@angular/common/http';
import {AuthorService} from './author.service';
import {AuthorFormComponent} from './author-form/author-form.component';
import {FormsModule} from '@angular/forms';
import {AuthorComponent} from './author/author.component';
import {AuthorCreatedComponent} from './author-created/author-created.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    ContactUsComponent,
    AuthorsComponent,
    RoutingComponent,
    AuthorFormComponent,
    AuthorComponent,
    AuthorCreatedComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [AuthorService],
  bootstrap: [AppComponent]
})
export class AppModule { }
