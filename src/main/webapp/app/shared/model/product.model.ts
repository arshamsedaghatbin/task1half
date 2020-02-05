import { ICustomer } from 'app/shared/model/customer.model';

export interface IProduct {
  id?: number;
  name?: string;
  customer?: ICustomer;
}

export class Product implements IProduct {
  constructor(public id?: number, public name?: string, public customer?: ICustomer) {}
}
