import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDataSource } from 'app/shared/model/data-source.model';
import { AccountService } from 'app/core';
import { DataSourceService } from './data-source.service';

@Component({
  selector: 'jhi-data-source',
  templateUrl: './data-source.component.html'
})
export class DataSourceComponent implements OnInit, OnDestroy {
  dataSources: IDataSource[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dataSourceService: DataSourceService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dataSourceService
      .query()
      .pipe(
        filter((res: HttpResponse<IDataSource[]>) => res.ok),
        map((res: HttpResponse<IDataSource[]>) => res.body)
      )
      .subscribe(
        (res: IDataSource[]) => {
          this.dataSources = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDataSources();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDataSource) {
    return item.id;
  }

  registerChangeInDataSources() {
    this.eventSubscriber = this.eventManager.subscribe('dataSourceListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
