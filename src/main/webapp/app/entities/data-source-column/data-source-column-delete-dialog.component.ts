import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataSourceColumn } from 'app/shared/model/data-source-column.model';
import { DataSourceColumnService } from './data-source-column.service';

@Component({
  selector: 'jhi-data-source-column-delete-dialog',
  templateUrl: './data-source-column-delete-dialog.component.html'
})
export class DataSourceColumnDeleteDialogComponent {
  dataSourceColumn: IDataSourceColumn;

  constructor(
    protected dataSourceColumnService: DataSourceColumnService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dataSourceColumnService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dataSourceColumnListModification',
        content: 'Deleted an dataSourceColumn'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-data-source-column-delete-popup',
  template: ''
})
export class DataSourceColumnDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataSourceColumn }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DataSourceColumnDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dataSourceColumn = dataSourceColumn;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/data-source-column', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/data-source-column', { outlets: { popup: null } }]);
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
