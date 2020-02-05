import { IProduct } from 'app/shared/model/product.model';
import { IProductUser } from 'app/shared/model/product-user.model';

export interface IOffer {
  id?: number;
  departmentName?: string;
  product?: IProduct;
  productUser?: IProductUser;
}

export class Offer implements IOffer {
  constructor(public id?: number, public departmentName?: string, public product?: IProduct, public productUser?: IProductUser) {}
}
