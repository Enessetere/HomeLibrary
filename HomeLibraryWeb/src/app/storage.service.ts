import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  state: any;

  constructor() { }

  setState(state: any) {
    this.state = state;
  }

  getState() {
    return this.state;
  }
}
