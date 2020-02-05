import { IProduct } from 'app/shared/model/product.model';

export interface ICustomer {
  id?: number;
  name?: string;
  lastName?: string;
  products?: IProduct[];
}

export class Customer implements ICustomer {
  constructor(public id?: number, public name?: string, public lastName?: string, public products?: IProduct[]) {}
}
