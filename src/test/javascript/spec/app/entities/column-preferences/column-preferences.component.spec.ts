/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { BzoneTestModule } from '../../../test.module';
import { ColumnPreferencesComponent } from 'app/entities/column-preferences/column-preferences.component';
import { ColumnPreferencesService } from 'app/entities/column-preferences/column-preferences.service';
import { ColumnPreferences } from 'app/shared/model/column-preferences.model';

describe('Component Tests', () => {
  describe('ColumnPreferences Management Component', () => {
    let comp: ColumnPreferencesComponent;
    let fixture: ComponentFixture<ColumnPreferencesComponent>;
    let service: ColumnPreferencesService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [ColumnPreferencesComponent],
        providers: []
      })
        .overrideTemplate(ColumnPreferencesComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ColumnPreferencesComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ColumnPreferencesService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ColumnPreferences(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.columnPreferences[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
