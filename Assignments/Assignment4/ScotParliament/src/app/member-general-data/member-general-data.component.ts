import {Component, Input, OnInit} from '@angular/core';
import {ParliamentMemberEntry} from "../model/parliament.member.entry";
import {GenderEnum} from "../model/gender.enum";
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-member-general-data',
  templateUrl: './member-general-data.component.html',
  styleUrls: ['./member-general-data.component.css']
})
export class MemberGeneralDataComponent implements OnInit {

  @Input() data!: ParliamentMemberEntry | null
  constructor() { }

  ngOnInit(): void {
  }

  getDefaultImage(genderType: GenderEnum): string{

    if(genderType === GenderEnum.MALE){
      return 'assets/image/male.svg';
    } else {
      return 'assets/image/female.svg';
    }

  }

  getDate(date: string): string{

    const datePipe = new DatePipe('en-UK')
    return datePipe.transform(new Date(Date.parse(date)), 'MMM dd, YYYY')!;

  }
}
