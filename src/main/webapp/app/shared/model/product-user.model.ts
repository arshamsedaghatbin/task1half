import { IOffer } from 'app/shared/model/offer.model';
import { SourceType } from 'app/shared/model/enumerations/source-type.model';

export interface IProductUser {
  id?: number;
  name?: string;
  source?: SourceType;
  offers?: IOffer[];
}

export class ProductUser implements IProductUser {
  constructor(public id?: number, public name?: string, public source?: SourceType, public offers?: IOffer[]) {}
}
