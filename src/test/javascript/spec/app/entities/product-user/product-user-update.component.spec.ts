import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { Task1HalfTestModule } from '../../../test.module';
import { ProductUserUpdateComponent } from 'app/entities/product-user/product-user-update.component';
import { ProductUserService } from 'app/entities/product-user/product-user.service';
import { ProductUser } from 'app/shared/model/product-user.model';

describe('Component Tests', () => {
  describe('ProductUser Management Update Component', () => {
    let comp: ProductUserUpdateComponent;
    let fixture: ComponentFixture<ProductUserUpdateComponent>;
    let service: ProductUserService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [Task1HalfTestModule],
        declarations: [ProductUserUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProductUserUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProductUserUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProductUserService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductUser(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProductUser();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
