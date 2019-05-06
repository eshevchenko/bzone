import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDataSourceFile } from 'app/shared/model/data-source-file.model';
import { AccountService } from 'app/core';
import { DataSourceFileService } from './data-source-file.service';

@Component({
  selector: 'jhi-data-source-file',
  templateUrl: './data-source-file.component.html'
})
export class DataSourceFileComponent implements OnInit, OnDestroy {
  dataSourceFiles: IDataSourceFile[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected dataSourceFileService: DataSourceFileService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.dataSourceFileService
      .query()
      .pipe(
        filter((res: HttpResponse<IDataSourceFile[]>) => res.ok),
        map((res: HttpResponse<IDataSourceFile[]>) => res.body)
      )
      .subscribe(
        (res: IDataSourceFile[]) => {
          this.dataSourceFiles = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInDataSourceFiles();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IDataSourceFile) {
    return item.id;
  }

  registerChangeInDataSourceFiles() {
    this.eventSubscriber = this.eventManager.subscribe('dataSourceFileListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
