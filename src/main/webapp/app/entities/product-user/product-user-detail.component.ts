import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProductUser } from 'app/shared/model/product-user.model';

@Component({
  selector: 'jhi-product-user-detail',
  templateUrl: './product-user-detail.component.html'
})
export class ProductUserDetailComponent implements OnInit {
  productUser: IProductUser | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productUser }) => {
      this.productUser = productUser;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
