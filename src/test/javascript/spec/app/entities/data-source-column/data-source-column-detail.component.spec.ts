/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BzoneTestModule } from '../../../test.module';
import { DataSourceColumnDetailComponent } from 'app/entities/data-source-column/data-source-column-detail.component';
import { DataSourceColumn } from 'app/shared/model/data-source-column.model';

describe('Component Tests', () => {
  describe('DataSourceColumn Management Detail Component', () => {
    let comp: DataSourceColumnDetailComponent;
    let fixture: ComponentFixture<DataSourceColumnDetailComponent>;
    const route = ({ data: of({ dataSourceColumn: new DataSourceColumn(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataSourceColumnDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DataSourceColumnDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataSourceColumnDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dataSourceColumn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
