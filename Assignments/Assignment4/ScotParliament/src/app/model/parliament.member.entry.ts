import {GenderEnum} from "./gender.enum";

export interface ParliamentMemberEntry{

  readonly PersonID: number;
  readonly PhotoURL?: string;
  readonly Notes: string;
  readonly BirthDate: string;
  readonly ParliamentaryName: string;
  readonly GenderTypeID: GenderEnum;
  readonly IsCurrent: boolean;

}
