/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BzoneTestModule } from '../../../test.module';
import { DataCardColumnDetailComponent } from 'app/entities/data-card-column/data-card-column-detail.component';
import { DataCardColumn } from 'app/shared/model/data-card-column.model';

describe('Component Tests', () => {
  describe('DataCardColumn Management Detail Component', () => {
    let comp: DataCardColumnDetailComponent;
    let fixture: ComponentFixture<DataCardColumnDetailComponent>;
    const route = ({ data: of({ dataCardColumn: new DataCardColumn(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataCardColumnDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DataCardColumnDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataCardColumnDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dataCardColumn).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
