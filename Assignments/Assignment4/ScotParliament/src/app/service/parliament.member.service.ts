import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {ParliamentMemberEntry} from "../model/parliament.member.entry";
import {map, switchAll, switchMap, withLatestFrom} from "rxjs/operators";
import {ParliamentMember} from "../model/parliament.member";
import {ParliamentMemberWebsite} from "../model/parliament.member.website";
import {ParliamentWebsiteResponse} from "../model/response/parliament.website.response";
import {ParliamentMemberPartyInfo} from "../model/parliament.member.party.info";
import {ParliamentMemberPartyInfoResponse} from "../model/response/parliament.member.party.info.response";
import {ParliamentPartyInfoResponse} from "../model/response/parliament.party.info.response";
import {CacheService} from "./cache.service";

@Injectable({
  providedIn: 'root'
})
export class ParliamentMemberService {

  constructor(private httpClient: HttpClient, private cache: CacheService) { }

  public getMembersList(renewCache: boolean = false): Observable<ParliamentMemberEntry[]>{

    const url = 'https://data.parliament.scot/api/members';
    let obs: Observable<ParliamentMemberEntry[]>;
    if ( this.cache.cacheEmpty || renewCache ){
      this.cache.refreshCache();
      obs =  this.httpClient.get<ParliamentMemberEntry[]>(url);
      obs = obs.pipe(
        map((entries) => {
          for( const entry of entries ){
            const parliamentMember = new ParliamentMember(entry)
            this.cache.addToCache(entry.PersonID, parliamentMember);
          }
          return entries;
        })
      );
    } else {
      const res: ParliamentMemberEntry[] = [];
      this.cache?.forEach(member => res.push(member.entryData));
      obs = of(res);
    }
    return obs;

  }


  public getWebsites(personId: number, renewCache: boolean = false): Observable<ParliamentMemberWebsite[]>{
    const member = this.cache?.getFromCache(personId);
    const websites = member?.websites;

    let obs: Observable<ParliamentMemberWebsite[]>

    if( !websites || websites.length === 0 || renewCache ){
      const url = 'https://data.parliament.scot/api/websites';
      obs = this.httpClient.get<ParliamentWebsiteResponse[]>(url).pipe(
        map( websites => {
          this.cache?.forEach(member => member.websites = [])
          for ( const website of websites ){
            const w = new ParliamentMemberWebsite(website.WebURL, website.IsDefault);
            this.cache?.getFromCache(website.PersonID)?.websites?.push(w);
          }
          return websites.filter(website => website.PersonID === personId);
        }),
        map( websites => websites.map(website => new ParliamentMemberWebsite(website.WebURL, website.IsDefault)))
      );
    } else {
      obs = of(websites!);
    }
    return obs;
  }

  public getMemberEntry(personId: number, renewCache: boolean = false): Observable<ParliamentMemberEntry>{
    const member = this.cache?.getFromCache(personId);
    const entry = member?.entryData;
    let obs: Observable<ParliamentMemberEntry>;
    if( !entry || renewCache ){
      obs = this.getMembersList(renewCache).pipe(
        map((entries) => entries.filter(entry => entry.PersonID === personId)),
        map((entries) => entries[0])
      );
    } else {
      obs = of(entry);
    }
    return obs;
  }

  public getMember(personId: number): Observable<ParliamentMember>{

    let obs = this.getMemberEntry(personId).pipe(
      withLatestFrom(this.getWebsites(personId), this.getPartyInfo(personId)),
      switchMap(([entry, websites, parties]) => of(new ParliamentMember(entry, parties, websites))),
      map((member) => {
        this.cache?.addToCache(personId, member);
        return member;
      })
    );
    return obs;

  }

  public getPartyInfo(personId: number, renewCache: boolean = false): Observable<ParliamentMemberPartyInfo[]>{

    const partyURL = 'https://data.parliament.scot/api/parties';
    const membershipURL = 'https://data.parliament.scot/api/memberparties';
    const info = this.cache?.getFromCache(personId)?.partyInfo;
    let obs: Observable<ParliamentMemberPartyInfo[]>;
    if( !info || info.length === 0 || renewCache ){

      obs = this.httpClient.get<ParliamentPartyInfoResponse[]>(partyURL).pipe(
        map(parties => {
          const map = new Map<number, ParliamentPartyInfoResponse>();
          parties.forEach( party => map.set(party.ID, party))
          return map;
        }),
        withLatestFrom(this.httpClient.get<ParliamentMemberPartyInfoResponse[]>(membershipURL)),
        switchMap(([parties, members]) => of({parties, members})),
        map(( aggregated ) => {
          const res: ParliamentMemberPartyInfo[] = []
          this.cache?.forEach((member) => member.partyInfo = [])
          for( const member of aggregated.members ){
            const party = aggregated.parties.get(member.PartyID);
            const m = this.cache.getFromCache(member.PersonID);
            const info = new ParliamentMemberPartyInfo(party!.ActualName, member.ValidFromDate, member.ValidUntilDate);
            if( m ) {
              m!.partyInfo = m!.partyInfo ?? [];
              m!.partyInfo.push(info)
            }
            if( member.PersonID === personId ){
              res.push(info);
            }
          }
          return res;
        })
      );
      return obs;
    } else {
      obs = of(info);
    }

    return obs;

  }


}
