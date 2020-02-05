import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Task1HalfSharedModule } from 'app/shared/shared.module';
import { ProductUserComponent } from './product-user.component';
import { ProductUserDetailComponent } from './product-user-detail.component';
import { ProductUserUpdateComponent } from './product-user-update.component';
import { ProductUserDeleteDialogComponent } from './product-user-delete-dialog.component';
import { productUserRoute } from './product-user.route';

@NgModule({
  imports: [Task1HalfSharedModule, RouterModule.forChild(productUserRoute)],
  declarations: [ProductUserComponent, ProductUserDetailComponent, ProductUserUpdateComponent, ProductUserDeleteDialogComponent],
  entryComponents: [ProductUserDeleteDialogComponent]
})
export class Task1HalfProductUserModule {}
