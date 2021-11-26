import { TestBed } from '@angular/core/testing';

import { ParliamentMemberService } from './parliament.member.service';

describe('ParliamentMemberService', () => {
  let service: ParliamentMemberService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ParliamentMemberService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
