/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BzoneTestModule } from '../../../test.module';
import { DataCardDetailComponent } from 'app/entities/data-card/data-card-detail.component';
import { DataCard } from 'app/shared/model/data-card.model';

describe('Component Tests', () => {
  describe('DataCard Management Detail Component', () => {
    let comp: DataCardDetailComponent;
    let fixture: ComponentFixture<DataCardDetailComponent>;
    const route = ({ data: of({ dataCard: new DataCard(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataCardDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DataCardDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataCardDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dataCard).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
