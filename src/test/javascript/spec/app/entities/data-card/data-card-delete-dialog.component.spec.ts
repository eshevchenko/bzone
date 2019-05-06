/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BzoneTestModule } from '../../../test.module';
import { DataCardDeleteDialogComponent } from 'app/entities/data-card/data-card-delete-dialog.component';
import { DataCardService } from 'app/entities/data-card/data-card.service';

describe('Component Tests', () => {
  describe('DataCard Management Delete Component', () => {
    let comp: DataCardDeleteDialogComponent;
    let fixture: ComponentFixture<DataCardDeleteDialogComponent>;
    let service: DataCardService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataCardDeleteDialogComponent]
      })
        .overrideTemplate(DataCardDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataCardDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataCardService);
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
