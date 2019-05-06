/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BzoneTestModule } from '../../../test.module';
import { DataCardColumnComponent } from 'app/entities/data-card-column/data-card-column.component';
import { DataCardColumnService } from 'app/entities/data-card-column/data-card-column.service';
import { DataCardColumn } from 'app/shared/model/data-card-column.model';

describe('Component Tests', () => {
  describe('DataCardColumn Management Component', () => {
    let comp: DataCardColumnComponent;
    let fixture: ComponentFixture<DataCardColumnComponent>;
    let service: DataCardColumnService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataCardColumnComponent],
        providers: []
      })
        .overrideTemplate(DataCardColumnComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DataCardColumnComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataCardColumnService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new DataCardColumn(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dataCardColumns[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
