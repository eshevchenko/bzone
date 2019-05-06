import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDataCardColumn } from 'app/shared/model/data-card-column.model';
import { AccountService } from 'app/core';
import { DataCardColumnService } from './data-card-column.service';

@Component({
  selector: 'jhi-data-card-column',
  templateUrl: './data-card-column.component.html'
})
export class DataCardColumnComponent implements OnInit, OnDestroy {
  dataCardColumns: IDataCardColumn[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dataCardColumnService: DataCardColumnService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dataCardColumnService
      .query()
      .pipe(
        filter((res: HttpResponse<IDataCardColumn[]>) => res.ok),
        map((res: HttpResponse<IDataCardColumn[]>) => res.body)
      )
      .subscribe(
        (res: IDataCardColumn[]) => {
          this.dataCardColumns = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDataCardColumns();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDataCardColumn) {
    return item.id;
  }

  registerChangeInDataCardColumns() {
    this.eventSubscriber = this.eventManager.subscribe('dataCardColumnListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
