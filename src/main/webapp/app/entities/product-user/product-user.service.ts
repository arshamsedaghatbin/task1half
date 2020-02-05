import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IProductUser } from 'app/shared/model/product-user.model';

type EntityResponseType = HttpResponse<IProductUser>;
type EntityArrayResponseType = HttpResponse<IProductUser[]>;

@Injectable({ providedIn: 'root' })
export class ProductUserService {
  public resourceUrl = SERVER_API_URL + 'api/product-users';

  constructor(protected http: HttpClient) {}

  create(productUser: IProductUser): Observable<EntityResponseType> {
    return this.http.post<IProductUser>(this.resourceUrl, productUser, { observe: 'response' });
  }

  update(productUser: IProductUser): Observable<EntityResponseType> {
    return this.http.put<IProductUser>(this.resourceUrl, productUser, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IProductUser>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IProductUser[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
