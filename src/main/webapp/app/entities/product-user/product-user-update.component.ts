import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IProductUser, ProductUser } from 'app/shared/model/product-user.model';
import { ProductUserService } from './product-user.service';

@Component({
  selector: 'jhi-product-user-update',
  templateUrl: './product-user-update.component.html'
})
export class ProductUserUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [],
    source: []
  });

  constructor(protected productUserService: ProductUserService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ productUser }) => {
      this.updateForm(productUser);
    });
  }

  updateForm(productUser: IProductUser): void {
    this.editForm.patchValue({
      id: productUser.id,
      name: productUser.name,
      source: productUser.source
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const productUser = this.createFromForm();
    if (productUser.id !== undefined) {
      this.subscribeToSaveResponse(this.productUserService.update(productUser));
    } else {
      this.subscribeToSaveResponse(this.productUserService.create(productUser));
    }
  }

  private createFromForm(): IProductUser {
    return {
      ...new ProductUser(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      source: this.editForm.get(['source'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProductUser>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
