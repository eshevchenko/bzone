/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BzoneTestModule } from '../../../test.module';
import { ColumnPreferencesDetailComponent } from 'app/entities/column-preferences/column-preferences-detail.component';
import { ColumnPreferences } from 'app/shared/model/column-preferences.model';

describe('Component Tests', () => {
  describe('ColumnPreferences Management Detail Component', () => {
    let comp: ColumnPreferencesDetailComponent;
    let fixture: ComponentFixture<ColumnPreferencesDetailComponent>;
    const route = ({ data: of({ columnPreferences: new ColumnPreferences(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [ColumnPreferencesDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ColumnPreferencesDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ColumnPreferencesDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.columnPreferences).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
