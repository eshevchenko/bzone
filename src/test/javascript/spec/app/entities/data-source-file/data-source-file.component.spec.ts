/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BzoneTestModule } from '../../../test.module';
import { DataSourceFileComponent } from 'app/entities/data-source-file/data-source-file.component';
import { DataSourceFileService } from 'app/entities/data-source-file/data-source-file.service';
import { DataSourceFile } from 'app/shared/model/data-source-file.model';

describe('Component Tests', () => {
  describe('DataSourceFile Management Component', () => {
    let comp: DataSourceFileComponent;
    let fixture: ComponentFixture<DataSourceFileComponent>;
    let service: DataSourceFileService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataSourceFileComponent],
        providers: []
      })
        .overrideTemplate(DataSourceFileComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataSourceFileComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataSourceFileService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DataSourceFile(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dataSourceFiles[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
