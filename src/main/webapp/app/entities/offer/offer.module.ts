import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { Task1HalfSharedModule } from 'app/shared/shared.module';
import { OfferComponent } from './offer.component';
import { OfferDetailComponent } from './offer-detail.component';
import { OfferUpdateComponent } from './offer-update.component';
import { OfferDeleteDialogComponent } from './offer-delete-dialog.component';
import { offerRoute } from './offer.route';

@NgModule({
  imports: [Task1HalfSharedModule, RouterModule.forChild(offerRoute)],
  declarations: [OfferComponent, OfferDetailComponent, OfferUpdateComponent, OfferDeleteDialogComponent],
  entryComponents: [OfferDeleteDialogComponent]
})
export class Task1HalfOfferModule {}
