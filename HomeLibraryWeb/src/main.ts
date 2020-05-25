import { enableProdMode } from '@angular/core';
import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';

import { AppModule } from './app/app.module';
import { environment } from './environments/environment';
import { FooterModule } from './footer/footer.module';

if (environment.production) {
  enableProdMode();
}

const platformRef = platformBrowserDynamic();
platformRef.bootstrapModule(AppModule)
  .catch(err => console.error(err));

platformRef.bootstrapModule(FooterModule)
  .catch(err => console.error(err));
