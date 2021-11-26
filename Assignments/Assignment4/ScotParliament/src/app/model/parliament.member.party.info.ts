export class ParliamentMemberPartyInfo {

  private _ActualName: string;
  private _ValidFromDate: string;
  private _ValidUntilDate?: string;


  constructor(ActualName: string, ValidFromDate: string, ValidUntilDate?: string, ) {
    this._ActualName = ActualName;
    this._ValidUntilDate = ValidUntilDate;
    this._ValidFromDate = ValidFromDate;
  }

  get ActualName(): string {
    return this._ActualName;
  }

  set ActualName(value: string) {
    this._ActualName = value;
  }

  get ValidUntilDate(): string | undefined{
    return this._ValidUntilDate;
  }

  set ValidUntilDate(value: string | undefined) {
    this._ValidUntilDate = value;
  }

  get ValidFromDate(): string {
    return this._ValidFromDate;
  }

  set ValidFromDate(value: string) {
    this._ValidFromDate = value;
  }
}
