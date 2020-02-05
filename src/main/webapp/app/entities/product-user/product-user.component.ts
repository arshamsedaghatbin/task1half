import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProductUser } from 'app/shared/model/product-user.model';
import { ProductUserService } from './product-user.service';
import { ProductUserDeleteDialogComponent } from './product-user-delete-dialog.component';

@Component({
  selector: 'jhi-product-user',
  templateUrl: './product-user.component.html'
})
export class ProductUserComponent implements OnInit, OnDestroy {
  productUsers?: IProductUser[];
  eventSubscriber?: Subscription;

  constructor(
    protected productUserService: ProductUserService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.productUserService.query().subscribe((res: HttpResponse<IProductUser[]>) => {
      this.productUsers = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProductUsers();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProductUser): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProductUsers(): void {
    this.eventSubscriber = this.eventManager.subscribe('productUserListModification', () => this.loadAll());
  }

  delete(productUser: IProductUser): void {
    const modalRef = this.modalService.open(ProductUserDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.productUser = productUser;
  }
}
