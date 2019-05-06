import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IFlow, Flow } from 'app/shared/model/flow.model';
import { FlowService } from './flow.service';
import { IDataCard } from 'app/shared/model/data-card.model';
import { DataCardService } from 'app/entities/data-card';

@Component({
  selector: 'jhi-flow-update',
  templateUrl: './flow-update.component.html'
})
export class FlowUpdateComponent implements OnInit {
  flow: IFlow;
  isSaving: boolean;

  datacards: IDataCard[];

  editForm = this.fb.group({
    id: [],
    config: [null, [Validators.required]],
    context: [null, [Validators.required]],
    activeStep: [null, [Validators.required]],
    dataCardId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected flowService: FlowService,
    protected dataCardService: DataCardService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ flow }) => {
      this.updateForm(flow);
      this.flow = flow;
    });
    this.dataCardService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDataCard[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDataCard[]>) => response.body)
      )
      .subscribe((res: IDataCard[]) => (this.datacards = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(flow: IFlow) {
    this.editForm.patchValue({
      id: flow.id,
      config: flow.config,
      context: flow.context,
      activeStep: flow.activeStep,
      dataCardId: flow.dataCardId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const flow = this.createFromForm();
    if (flow.id !== undefined) {
      this.subscribeToSaveResponse(this.flowService.update(flow));
    } else {
      this.subscribeToSaveResponse(this.flowService.create(flow));
    }
  }

  private createFromForm(): IFlow {
    const entity = {
      ...new Flow(),
      id: this.editForm.get(['id']).value,
      config: this.editForm.get(['config']).value,
      context: this.editForm.get(['context']).value,
      activeStep: this.editForm.get(['activeStep']).value,
      dataCardId: this.editForm.get(['dataCardId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFlow>>) {
    result.subscribe((res: HttpResponse<IFlow>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackDataCardById(index: number, item: IDataCard) {
    return item.id;
  }
}
