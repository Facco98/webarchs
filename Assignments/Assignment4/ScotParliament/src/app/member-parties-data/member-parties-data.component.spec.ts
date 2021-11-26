import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MemberPartiesDataComponent } from './member-parties-data.component';

describe('MemberPartiesDataComponent', () => {
  let component: MemberPartiesDataComponent;
  let fixture: ComponentFixture<MemberPartiesDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MemberPartiesDataComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MemberPartiesDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
