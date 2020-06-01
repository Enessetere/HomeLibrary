import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {ContactUsComponent} from './contact-us/contact-us.component';
import {AuthorsComponent} from './authors/authors.component';

const routes: Routes = [
  {path: `home`, component: HomeComponent},
  {path: `contact`, component: ContactUsComponent},
  {path: `authors`, component: AuthorsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
export const RoutingComponent = [HomeComponent, ContactUsComponent, AuthorsComponent];
