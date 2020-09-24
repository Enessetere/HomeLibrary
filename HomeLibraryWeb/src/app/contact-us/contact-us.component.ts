import { Component, OnInit } from '@angular/core';
import {EmailService} from '../email.service';
import {Router} from '@angular/router';
import {Email} from '../email';

@Component({
  selector: 'app-contact-us',
  templateUrl: './contact-us.component.html',
  styleUrls: ['./contact-us.component.css']
})
export class ContactUsComponent implements OnInit {

  error = null;

  constructor(private emailService: EmailService, private router: Router) { }

  ngOnInit(): void {
  }

  onClickSubmit(emailForm: Email) {
    console.log(emailForm);
    this.emailService.sendData(emailForm).subscribe(
      () => this.router.navigate(['contact']),
      err => this.error = err.error);
  }

  resize() {
    const area = document.getElementById('message');
    setTimeout(() => {
      area.style.cssText = 'height:auto;';
      area.style.cssText = 'height:' + area.scrollHeight + 'px;';
    }, 0);
  }
}
