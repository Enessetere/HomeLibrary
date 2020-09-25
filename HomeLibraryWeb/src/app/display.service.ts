import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class DisplayService {

  constructor() { }

  changeDisplay(name: string) {
    let out = '';
    for (let idx = 0; idx < name.length; idx++) {
      if (name.charAt(idx).match(/[A-ZŚĆŻŹĄĘŁŃÓ]/g)) {
        out = out.concat('<span style="color: #CF3E03; font-weight: bold; font-size: 20px">', name.charAt(idx), '</span>');
      } else {
        out = out.concat(name.charAt(idx));
      }
    }
    return out;
  }
}
