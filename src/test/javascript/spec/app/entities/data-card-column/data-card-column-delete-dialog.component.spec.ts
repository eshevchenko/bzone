/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BzoneTestModule } from '../../../test.module';
import { DataCardColumnDeleteDialogComponent } from 'app/entities/data-card-column/data-card-column-delete-dialog.component';
import { DataCardColumnService } from 'app/entities/data-card-column/data-card-column.service';

describe('Component Tests', () => {
  describe('DataCardColumn Management Delete Component', () => {
    let comp: DataCardColumnDeleteDialogComponent;
    let fixture: ComponentFixture<DataCardColumnDeleteDialogComponent>;
    let service: DataCardColumnService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataCardColumnDeleteDialogComponent]
      })
        .overrideTemplate(DataCardColumnDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataCardColumnDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataCardColumnService);
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
