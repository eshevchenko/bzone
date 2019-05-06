/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BzoneTestModule } from '../../../test.module';
import { DataCardUpdateComponent } from 'app/entities/data-card/data-card-update.component';
import { DataCardService } from 'app/entities/data-card/data-card.service';
import { DataCard } from 'app/shared/model/data-card.model';

describe('Component Tests', () => {
  describe('DataCard Management Update Component', () => {
    let comp: DataCardUpdateComponent;
    let fixture: ComponentFixture<DataCardUpdateComponent>;
    let service: DataCardService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataCardUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DataCardUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataCardUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataCardService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DataCard(123);
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
        const entity = new DataCard();
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
