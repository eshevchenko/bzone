import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IColumnPreferences } from 'app/shared/model/column-preferences.model';
import { ColumnPreferencesService } from './column-preferences.service';

@Component({
  selector: 'jhi-column-preferences-delete-dialog',
  templateUrl: './column-preferences-delete-dialog.component.html'
})
export class ColumnPreferencesDeleteDialogComponent {
  columnPreferences: IColumnPreferences;

  constructor(
    protected columnPreferencesService: ColumnPreferencesService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.columnPreferencesService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'columnPreferencesListModification',
        content: 'Deleted an columnPreferences'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-column-preferences-delete-popup',
  template: ''
})
export class ColumnPreferencesDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ columnPreferences }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ColumnPreferencesDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.columnPreferences = columnPreferences;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/column-preferences', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/column-preferences', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
