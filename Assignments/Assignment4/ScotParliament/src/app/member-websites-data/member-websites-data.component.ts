import {Component, Input, OnInit} from '@angular/core';
import {ParliamentMemberWebsite} from "../model/parliament.member.website";

@Component({
  selector: 'app-member-websites-data',
  templateUrl: './member-websites-data.component.html',
  styleUrls: ['./member-websites-data.component.css']
})
export class MemberWebsitesDataComponent implements OnInit {

  @Input() websites!: ParliamentMemberWebsite[] | null;

  constructor() { }

  ngOnInit(): void {
  }

}
