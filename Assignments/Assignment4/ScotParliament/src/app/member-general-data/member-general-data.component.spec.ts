import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberGeneralDataComponent } from './member-general-data.component';

describe('MemberGeneralDataComponent', () => {
  let component: MemberGeneralDataComponent;
  let fixture: ComponentFixture<MemberGeneralDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemberGeneralDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberGeneralDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
