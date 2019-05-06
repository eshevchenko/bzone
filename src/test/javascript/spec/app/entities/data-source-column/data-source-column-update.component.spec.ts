/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BzoneTestModule } from '../../../test.module';
import { DataSourceColumnUpdateComponent } from 'app/entities/data-source-column/data-source-column-update.component';
import { DataSourceColumnService } from 'app/entities/data-source-column/data-source-column.service';
import { DataSourceColumn } from 'app/shared/model/data-source-column.model';

describe('Component Tests', () => {
  describe('DataSourceColumn Management Update Component', () => {
    let comp: DataSourceColumnUpdateComponent;
    let fixture: ComponentFixture<DataSourceColumnUpdateComponent>;
    let service: DataSourceColumnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataSourceColumnUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DataSourceColumnUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataSourceColumnUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataSourceColumnService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DataSourceColumn(123);
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
        const entity = new DataSourceColumn();
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
