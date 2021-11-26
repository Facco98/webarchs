import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberWebsitesDataComponent } from './member-websites-data.component';

describe('MemberWebsitesDataComponent', () => {
  let component: MemberWebsitesDataComponent;
  let fixture: ComponentFixture<MemberWebsitesDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemberWebsitesDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberWebsitesDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
