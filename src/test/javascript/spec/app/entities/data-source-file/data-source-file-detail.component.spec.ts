/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { BzoneTestModule } from '../../../test.module';
import { DataSourceFileDetailComponent } from 'app/entities/data-source-file/data-source-file-detail.component';
import { DataSourceFile } from 'app/shared/model/data-source-file.model';

describe('Component Tests', () => {
  describe('DataSourceFile Management Detail Component', () => {
    let comp: DataSourceFileDetailComponent;
    let fixture: ComponentFixture<DataSourceFileDetailComponent>;
    const route = ({ data: of({ dataSourceFile: new DataSourceFile(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataSourceFileDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DataSourceFileDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataSourceFileDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dataSourceFile).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
