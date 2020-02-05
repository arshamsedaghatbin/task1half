import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProductUser, ProductUser } from 'app/shared/model/product-user.model';
import { ProductUserService } from './product-user.service';
import { ProductUserComponent } from './product-user.component';
import { ProductUserDetailComponent } from './product-user-detail.component';
import { ProductUserUpdateComponent } from './product-user-update.component';

@Injectable({ providedIn: 'root' })
export class ProductUserResolve implements Resolve<IProductUser> {
  constructor(private service: ProductUserService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProductUser> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((productUser: HttpResponse<ProductUser>) => {
          if (productUser.body) {
            return of(productUser.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProductUser());
  }
}

export const productUserRoute: Routes = [
  {
    path: '',
    component: ProductUserComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'task1HalfApp.productUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProductUserDetailComponent,
    resolve: {
      productUser: ProductUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'task1HalfApp.productUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProductUserUpdateComponent,
    resolve: {
      productUser: ProductUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'task1HalfApp.productUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProductUserUpdateComponent,
    resolve: {
      productUser: ProductUserResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'task1HalfApp.productUser.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
