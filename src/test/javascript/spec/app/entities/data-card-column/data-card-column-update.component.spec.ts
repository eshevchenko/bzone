/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BzoneTestModule } from '../../../test.module';
import { DataCardColumnUpdateComponent } from 'app/entities/data-card-column/data-card-column-update.component';
import { DataCardColumnService } from 'app/entities/data-card-column/data-card-column.service';
import { DataCardColumn } from 'app/shared/model/data-card-column.model';

describe('Component Tests', () => {
  describe('DataCardColumn Management Update Component', () => {
    let comp: DataCardColumnUpdateComponent;
    let fixture: ComponentFixture<DataCardColumnUpdateComponent>;
    let service: DataCardColumnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataCardColumnUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DataCardColumnUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataCardColumnUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataCardColumnService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DataCardColumn(123);
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
        const entity = new DataCardColumn();
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
