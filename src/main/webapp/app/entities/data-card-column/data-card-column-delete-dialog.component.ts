import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataCardColumn } from 'app/shared/model/data-card-column.model';
import { DataCardColumnService } from './data-card-column.service';

@Component({
  selector: 'jhi-data-card-column-delete-dialog',
  templateUrl: './data-card-column-delete-dialog.component.html'
})
export class DataCardColumnDeleteDialogComponent {
  dataCardColumn: IDataCardColumn;

  constructor(
    protected dataCardColumnService: DataCardColumnService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dataCardColumnService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dataCardColumnListModification',
        content: 'Deleted an dataCardColumn'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-data-card-column-delete-popup',
  template: ''
})
export class DataCardColumnDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataCardColumn }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DataCardColumnDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dataCardColumn = dataCardColumn;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/data-card-column', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/data-card-column', { outlets: { popup: null } }]);
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
