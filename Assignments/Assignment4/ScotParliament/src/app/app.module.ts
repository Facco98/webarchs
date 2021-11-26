import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import {RouterModule} from "@angular/router";
import { MembersListComponent } from './members-list/members-list.component';
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatTableDataSource, MatTableModule} from "@angular/material/table";
import {MatToolbar, MatToolbarModule} from "@angular/material/toolbar";
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import {MatPaginatorModule} from "@angular/material/paginator";
import { MemberCardComponent } from './member-card/member-card.component';
import {MatCardModule} from "@angular/material/card";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";
import { MemberDetailsComponent } from './member-details/member-details.component';
import { MemberGeneralDataComponent } from './member-general-data/member-general-data.component';
import { MemberPartiesDataComponent } from './member-parties-data/member-parties-data.component';
import { MemberWebsitesDataComponent } from './member-websites-data/member-websites-data.component';
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {FormsModule} from "@angular/forms";

const routes = [
  {
    path: 'list',
    component: MembersListComponent
  },
  {
    path: 'memberDetails/:personID',
    component: MemberDetailsComponent
  },
  {
    path: '**',
    redirectTo: '/list',
    pathMatch: 'full'
  }
]

@NgModule({
  declarations: [
    AppComponent,
    MembersListComponent,
    MemberCardComponent,
    MemberDetailsComponent,
    MemberGeneralDataComponent,
    MemberPartiesDataComponent,
    MemberWebsitesDataComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes, {useHash: true}),
    HttpClientModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatToolbarModule,
    MatProgressSpinnerModule,
    MatPaginatorModule,
    MatCardModule,
    MatFormFieldModule,
    MatCheckboxModule,
    MatSlideToggleModule,
    MatSnackBarModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
