import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProductUser } from 'app/shared/model/product-user.model';
import { ProductUserService } from './product-user.service';

@Component({
  templateUrl: './product-user-delete-dialog.component.html'
})
export class ProductUserDeleteDialogComponent {
  productUser?: IProductUser;

  constructor(
    protected productUserService: ProductUserService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.productUserService.delete(id).subscribe(() => {
      this.eventManager.broadcast('productUserListModification');
      this.activeModal.close();
    });
  }
}
