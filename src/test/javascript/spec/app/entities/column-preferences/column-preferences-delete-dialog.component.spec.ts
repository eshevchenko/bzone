/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { BzoneTestModule } from '../../../test.module';
import { ColumnPreferencesDeleteDialogComponent } from 'app/entities/column-preferences/column-preferences-delete-dialog.component';
import { ColumnPreferencesService } from 'app/entities/column-preferences/column-preferences.service';

describe('Component Tests', () => {
  describe('ColumnPreferences Management Delete Component', () => {
    let comp: ColumnPreferencesDeleteDialogComponent;
    let fixture: ComponentFixture<ColumnPreferencesDeleteDialogComponent>;
    let service: ColumnPreferencesService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [BzoneTestModule],
        declarations: [ColumnPreferencesDeleteDialogComponent]
      })
        .overrideTemplate(ColumnPreferencesDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ColumnPreferencesDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ColumnPreferencesService);
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
