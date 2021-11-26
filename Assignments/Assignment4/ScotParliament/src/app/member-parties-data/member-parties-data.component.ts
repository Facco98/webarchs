import {Component, Input, OnInit} from '@angular/core';
import {ParliamentMemberPartyInfo} from "../model/parliament.member.party.info";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-member-parties-data',
  templateUrl: './member-parties-data.component.html',
  styleUrls: ['./member-parties-data.component.css']
})
export class MemberPartiesDataComponent implements OnInit {

  @Input() parties!: ParliamentMemberPartyInfo[] | null;
  constructor() { }

  ngOnInit(): void {
  }

  getDate(date: string): string{

    const datePipe = new DatePipe('en-UK')
    return datePipe.transform(new Date(Date.parse(date)), 'MMM dd, YYYY')!;

  }

}
