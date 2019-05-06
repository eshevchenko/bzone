import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IColumnPreferences } from 'app/shared/model/column-preferences.model';
import { AccountService } from 'app/core';
import { ColumnPreferencesService } from './column-preferences.service';

@Component({
  selector: 'jhi-column-preferences',
  templateUrl: './column-preferences.component.html'
})
export class ColumnPreferencesComponent implements OnInit, OnDestroy {
  columnPreferences: IColumnPreferences[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected columnPreferencesService: ColumnPreferencesService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.columnPreferencesService
      .query()
      .pipe(
        filter((res: HttpResponse<IColumnPreferences[]>) => res.ok),
        map((res: HttpResponse<IColumnPreferences[]>) => res.body)
      )
      .subscribe(
        (res: IColumnPreferences[]) => {
          this.columnPreferences = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInColumnPreferences();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IColumnPreferences) {
    return item.id;
  }

  registerChangeInColumnPreferences() {
    this.eventSubscriber = this.eventManager.subscribe('columnPreferencesListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
