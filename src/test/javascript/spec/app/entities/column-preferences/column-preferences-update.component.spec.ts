/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BzoneTestModule } from '../../../test.module';
import { ColumnPreferencesUpdateComponent } from 'app/entities/column-preferences/column-preferences-update.component';
import { ColumnPreferencesService } from 'app/entities/column-preferences/column-preferences.service';
import { ColumnPreferences } from 'app/shared/model/column-preferences.model';

describe('Component Tests', () => {
  describe('ColumnPreferences Management Update Component', () => {
    let comp: ColumnPreferencesUpdateComponent;
    let fixture: ComponentFixture<ColumnPreferencesUpdateComponent>;
    let service: ColumnPreferencesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [ColumnPreferencesUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ColumnPreferencesUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ColumnPreferencesUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ColumnPreferencesService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ColumnPreferences(123);
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
        const entity = new ColumnPreferences();
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
