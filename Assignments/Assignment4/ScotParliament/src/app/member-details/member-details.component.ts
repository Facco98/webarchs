import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {ParliamentMemberService} from "../service/parliament.member.service";
import {ParliamentMember} from "../model/parliament.member";
import {ParliamentMemberPartyInfo} from "../model/parliament.member.party.info";
import {ParliamentMemberEntry} from "../model/parliament.member.entry";
import {ParliamentMemberWebsite} from "../model/parliament.member.website";
import {Observable} from "rxjs";
import {catchError} from "rxjs/operators";

@Component({
  selector: 'app-member-details',
  templateUrl: './member-details.component.html',
  styleUrls: ['./member-details.component.css']
})
export class MemberDetailsComponent implements OnInit {

  generalData$!: Observable<ParliamentMemberEntry>;
  parties$!: Observable<ParliamentMemberPartyInfo[]>;
  websites$!: Observable<ParliamentMemberWebsite[]>;

  constructor(private route: ActivatedRoute, private memberService: ParliamentMemberService, private router: Router) { }

  ngOnInit(): void {

    this.route.params.subscribe(params => {
      if( params.personID ) {
        const personID = Number(params.personID);
        this.loadData(personID);
      }
    });

  }

  private loadData(personID: number): void{

    this.generalData$ = this.memberService.getMemberEntry(personID);
    this.parties$ = this.memberService.getPartyInfo(personID);
    this.websites$ = this.memberService.getWebsites(personID);

    this.generalData$.subscribe(val => {
      if( !val ){
        this.router.navigate(['/']);
      }

    });

    this.parties$.subscribe(val => {
      if( !val ){
        this.router.navigate(['/']);
      }
    });

    this.websites$.subscribe(val => {
      if( !val ){
        this.router.navigate(['/']);
      }
    });





  }

}
