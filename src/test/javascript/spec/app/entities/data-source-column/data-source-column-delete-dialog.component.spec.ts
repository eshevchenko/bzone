/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BzoneTestModule } from '../../../test.module';
import { DataSourceColumnDeleteDialogComponent } from 'app/entities/data-source-column/data-source-column-delete-dialog.component';
import { DataSourceColumnService } from 'app/entities/data-source-column/data-source-column.service';

describe('Component Tests', () => {
  describe('DataSourceColumn Management Delete Component', () => {
    let comp: DataSourceColumnDeleteDialogComponent;
    let fixture: ComponentFixture<DataSourceColumnDeleteDialogComponent>;
    let service: DataSourceColumnService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataSourceColumnDeleteDialogComponent]
      })
        .overrideTemplate(DataSourceColumnDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataSourceColumnDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataSourceColumnService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
