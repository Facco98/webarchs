import { Injectable } from '@angular/core';
import {ParliamentMember} from "../model/parliament.member";

@Injectable({
  providedIn: 'root'
})
export class CacheService {

  private cache?: Map<number, ParliamentMember>;


  constructor() { }

  public get cacheEmpty(): boolean{
    return !this.cache;
  }

  public addToCache(key: number, object: ParliamentMember): void{

    if( !this.cache )
      this.cache = new Map<number, ParliamentMember>();

    this.cache.set(key, object);

  }

  public getFromCache(key: number): ParliamentMember | undefined{

    return this.cache?.get(key);

  }

  public refreshCache(): void{

    this.cache = new Map<number, ParliamentMember>();

  }

  public forEach(operator: (member: ParliamentMember) => void): void{
    if( this.cache )
      this.cache.forEach(operator);
  }


}
