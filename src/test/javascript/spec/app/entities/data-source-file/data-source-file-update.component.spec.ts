/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { BzoneTestModule } from '../../../test.module';
import { DataSourceFileUpdateComponent } from 'app/entities/data-source-file/data-source-file-update.component';
import { DataSourceFileService } from 'app/entities/data-source-file/data-source-file.service';
import { DataSourceFile } from 'app/shared/model/data-source-file.model';

describe('Component Tests', () => {
  describe('DataSourceFile Management Update Component', () => {
    let comp: DataSourceFileUpdateComponent;
    let fixture: ComponentFixture<DataSourceFileUpdateComponent>;
    let service: DataSourceFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataSourceFileUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DataSourceFileUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataSourceFileUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataSourceFileService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new DataSourceFile(123);
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
        const entity = new DataSourceFile();
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
