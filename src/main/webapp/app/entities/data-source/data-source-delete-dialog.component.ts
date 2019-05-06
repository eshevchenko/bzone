import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataSource } from 'app/shared/model/data-source.model';
import { DataSourceService } from './data-source.service';

@Component({
  selector: 'jhi-data-source-delete-dialog',
  templateUrl: './data-source-delete-dialog.component.html'
})
export class DataSourceDeleteDialogComponent {
  dataSource: IDataSource;

  constructor(
    protected dataSourceService: DataSourceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dataSourceService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dataSourceListModification',
        content: 'Deleted an dataSource'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-data-source-delete-popup',
  template: ''
})
export class DataSourceDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataSource }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DataSourceDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dataSource = dataSource;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/data-source', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/data-source', { outlets: { popup: null } }]);
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
