import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorFailureComponent } from './author-failure.component';

describe('AuthorFailureComponent', () => {
  let component: AuthorFailureComponent;
  let fixture: ComponentFixture<AuthorFailureComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorFailureComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorFailureComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
