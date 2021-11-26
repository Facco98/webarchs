export class ParliamentMemberWebsite {

  private _WebURL: string;
  private _IsDefault: boolean;

  constructor(WebURL: string, IsDefault: boolean) {
    this._WebURL = WebURL;
    this._IsDefault = IsDefault;
  }

  get WebURL(): string {
    return this._WebURL;
  }

  set WebURL(value: string) {
    this._WebURL = value;
  }

  get IsDefault(): boolean {
    return this._IsDefault;
  }

  set IsDefault(value: boolean) {
    this._IsDefault = value;
  }
}
