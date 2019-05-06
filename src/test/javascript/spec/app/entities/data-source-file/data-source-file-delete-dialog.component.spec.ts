/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BzoneTestModule } from '../../../test.module';
import { DataSourceFileDeleteDialogComponent } from 'app/entities/data-source-file/data-source-file-delete-dialog.component';
import { DataSourceFileService } from 'app/entities/data-source-file/data-source-file.service';

describe('Component Tests', () => {
  describe('DataSourceFile Management Delete Component', () => {
    let comp: DataSourceFileDeleteDialogComponent;
    let fixture: ComponentFixture<DataSourceFileDeleteDialogComponent>;
    let service: DataSourceFileService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [DataSourceFileDeleteDialogComponent]
      })
        .overrideTemplate(DataSourceFileDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DataSourceFileDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DataSourceFileService);
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
