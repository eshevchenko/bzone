import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFlow } from 'app/shared/model/flow.model';
import { AccountService } from 'app/core';
import { FlowService } from './flow.service';

@Component({
  selector: 'jhi-flow',
  templateUrl: './flow.component.html'
})
export class FlowComponent implements OnInit, OnDestroy {
  flows: IFlow[];
  currentAccount: any;
  eventSubscriber: Subscription;

  constructor(
    protected flowService: FlowService,
    protected jhiAlertService: JhiAlertService,
    protected eventManager: JhiEventManager,
    protected accountService: AccountService
  ) {}

  loadAll() {
    this.flowService
      .query()
      .pipe(
        filter((res: HttpResponse<IFlow[]>) => res.ok),
        map((res: HttpResponse<IFlow[]>) => res.body)
      )
      .subscribe(
        (res: IFlow[]) => {
          this.flows = res;
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
  }

  ngOnInit() {
    this.loadAll();
    this.accountService.identity().then(account => {
      this.currentAccount = account;
    });
    this.registerChangeInFlows();
  }

  ngOnDestroy() {
    this.eventManager.destroy(this.eventSubscriber);
  }

  trackId(index: number, item: IFlow) {
    return item.id;
  }

  registerChangeInFlows() {
    this.eventSubscriber = this.eventManager.subscribe('flowListModification', response => this.loadAll());
  }

  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
