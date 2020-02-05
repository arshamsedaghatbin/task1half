import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { Task1HalfTestModule } from '../../../test.module';
import { ProductUserDetailComponent } from 'app/entities/product-user/product-user-detail.component';
import { ProductUser } from 'app/shared/model/product-user.model';

describe('Component Tests', () => {
  describe('ProductUser Management Detail Component', () => {
    let comp: ProductUserDetailComponent;
    let fixture: ComponentFixture<ProductUserDetailComponent>;
    const route = ({ data: of({ productUser: new ProductUser(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Task1HalfTestModule],
        declarations: [ProductUserDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProductUserDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProductUserDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load productUser on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.productUser).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
