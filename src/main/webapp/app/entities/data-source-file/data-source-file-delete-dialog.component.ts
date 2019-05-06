import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataSourceFile } from 'app/shared/model/data-source-file.model';
import { DataSourceFileService } from './data-source-file.service';

@Component({
  selector: 'jhi-data-source-file-delete-dialog',
  templateUrl: './data-source-file-delete-dialog.component.html'
})
export class DataSourceFileDeleteDialogComponent {
  dataSourceFile: IDataSourceFile;

  constructor(
    protected dataSourceFileService: DataSourceFileService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dataSourceFileService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dataSourceFileListModification',
        content: 'Deleted an dataSourceFile'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-data-source-file-delete-popup',
  template: ''
})
export class DataSourceFileDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataSourceFile }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DataSourceFileDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dataSourceFile = dataSourceFile;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/data-source-file', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/data-source-file', { outlets: { popup: null } }]);
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
