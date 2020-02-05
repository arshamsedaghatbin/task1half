import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { Task1HalfTestModule } from '../../../test.module';
import { ProductUserComponent } from 'app/entities/product-user/product-user.component';
import { ProductUserService } from 'app/entities/product-user/product-user.service';
import { ProductUser } from 'app/shared/model/product-user.model';

describe('Component Tests', () => {
  describe('ProductUser Management Component', () => {
    let comp: ProductUserComponent;
    let fixture: ComponentFixture<ProductUserComponent>;
    let service: ProductUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Task1HalfTestModule],
        declarations: [ProductUserComponent],
        providers: []
      })
        .overrideTemplate(ProductUserComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductUserComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductUserService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProductUser(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.productUsers && comp.productUsers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
