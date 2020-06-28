import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AuthorCreatedComponent } from './author-created.component';

describe('AuthorCreatedComponent', () => {
  let component: AuthorCreatedComponent;
  let fixture: ComponentFixture<AuthorCreatedComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AuthorCreatedComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AuthorCreatedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
