import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'customer',
        loadChildren: () => import('./customer/customer.module').then(m => m.Task1HalfCustomerModule)
      },
      {
        path: 'product',
        loadChildren: () => import('./product/product.module').then(m => m.Task1HalfProductModule)
      },
      {
        path: 'offer',
        loadChildren: () => import('./offer/offer.module').then(m => m.Task1HalfOfferModule)
      },
      {
        path: 'product-user',
        loadChildren: () => import('./product-user/product-user.module').then(m => m.Task1HalfProductUserModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class Task1HalfEntityModule {}
