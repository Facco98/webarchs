import {Component, Input, OnInit} from '@angular/core';
import {ParliamentMemberEntry} from "../model/parliament.member.entry";
import {GenderEnum} from "../model/gender.enum";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-member-card',
  templateUrl: './member-card.component.html',
  styleUrls: ['./member-card.component.css']
})
export class MemberCardComponent implements OnInit {

  @Input() member!: ParliamentMemberEntry;

  constructor(private router: Router, private snackBar: MatSnackBar) { }

  ngOnInit(): void {
  }

  getImage(): string{

    let imgUrl = this.member.PhotoURL;
    if ( !imgUrl || imgUrl.length === 0 ){
      return this.getDefaultImage();
    }
    return imgUrl;

  }

  cardDidClick(): void{

    if( !this.member.IsCurrent ) {
      let snackBarRef = this.snackBar.open('Can only view data of an active member',undefined, {
        duration: 1500,
        panelClass: ['mat-toolbar', 'mat-warn']
      });
    } else {
      if( this.snackBar._openedSnackBarRef ) {
        this.snackBar._openedSnackBarRef.dismiss();
      }
      this.router.navigate(['/memberDetails', this.member.PersonID]);
    }


  }

  getDefaultImage(): string{

    let imgUrl: string;
    if( this.member.GenderTypeID === GenderEnum.MALE ){
      imgUrl = '/assets/image/male.svg';
    } else {
      imgUrl = '/assets/image/female.svg';
    }
    return imgUrl;

  }

}
