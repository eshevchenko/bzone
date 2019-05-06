import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDataCard } from 'app/shared/model/data-card.model';
import { DataCardService } from './data-card.service';

@Component({
  selector: 'jhi-data-card-delete-dialog',
  templateUrl: './data-card-delete-dialog.component.html'
})
export class DataCardDeleteDialogComponent {
  dataCard: IDataCard;

  constructor(protected dataCardService: DataCardService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.dataCardService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'dataCardListModification',
        content: 'Deleted an dataCard'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-data-card-delete-popup',
  template: ''
})
export class DataCardDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataCard }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(DataCardDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.dataCard = dataCard;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/data-card', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/data-card', { outlets: { popup: null } }]);
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
