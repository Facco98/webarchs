import {ChangeDetectorRef, Component, DoCheck, OnInit, ViewChild} from '@angular/core';
import {ParliamentMemberService} from "../service/parliament.member.service";
import {ParliamentMember} from "../model/parliament.member";
import {ParliamentMemberEntry} from "../model/parliament.member.entry";
import {MatPaginator} from "@angular/material/paginator";
import {MatTableDataSource} from "@angular/material/table";
import {Observable} from "rxjs";
import {ChangeDetection} from "@angular/cli/lib/config/workspace-schema";

@Component({
  selector: 'app-members-list',
  templateUrl: './members-list.component.html',
  styleUrls: ['./members-list.component.css']
})
export class MembersListComponent implements OnInit , DoCheck{

  @ViewChild('paginator') paginator!: MatPaginator;

  paginated!: Observable<ParliamentMemberEntry[]>
  onlyActive = true;

  private dataSource!: MatTableDataSource<ParliamentMemberEntry>;
  private members!: ParliamentMemberEntry[];

  constructor(private memberService: ParliamentMemberService, private dcr: ChangeDetectorRef) { }

  ngOnInit(): void {
    this.memberService.getMembersList().subscribe(res => {
      this.setupDataSource(res);
    });
  }

  changed($event: any): void{
    this.onlyActive = $event.checked;
    this.dataSource.filter = ''+!this.dataSource.filter;
  }

  ngDoCheck(): void {
    if( this.dataSource && !this.dataSource.paginator ) {
      this.dataSource.paginator = this.paginator;
    }
    this.dcr.detectChanges();
  }

  private setupDataSource(members: ParliamentMemberEntry[]): void{

    this.members = members;
    this.dataSource = new MatTableDataSource<ParliamentMemberEntry>(this.members
      .sort((a,b) => a.ParliamentaryName.localeCompare(b.ParliamentaryName)));
    this.dataSource.filterPredicate = (member, _) => {
      if ( this.onlyActive ){
        return member.IsCurrent
      } else {
        return true;
      }
    }
    this.dataSource.filter = ''+!this.dataSource.filter;
    this.paginated = this.dataSource.connect();

  }


}
