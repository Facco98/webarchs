import {ParliamentMemberEntry} from "./parliament.member.entry";
import {ParliamentMemberPartyInfo} from "./parliament.member.party.info";
import {ParliamentMemberWebsite} from "./parliament.member.website";

export class ParliamentMember{

  private _entryData: ParliamentMemberEntry;
  private _partyInfo?: ParliamentMemberPartyInfo[];
  private _websites?: ParliamentMemberWebsite[]


  constructor(entryData: ParliamentMemberEntry, partyInfo?: ParliamentMemberPartyInfo[], websites?: ParliamentMemberWebsite[]) {
    this._entryData = entryData;
    this._partyInfo = partyInfo;
    this._websites = websites;
  }


  get entryData(): ParliamentMemberEntry {
    return this._entryData;
  }

  set entryData(value: ParliamentMemberEntry) {
    this._entryData = value;
  }

  get partyInfo(): ParliamentMemberPartyInfo[] | undefined {
    return this._partyInfo;
  }

  set partyInfo(value: ParliamentMemberPartyInfo[] | undefined) {
    this._partyInfo = value;
  }

  get websites(): ParliamentMemberWebsite[] | undefined {
    return this._websites;
  }

  set websites(value: ParliamentMemberWebsite[] | undefined) {
    this._websites = value;
  }

}
