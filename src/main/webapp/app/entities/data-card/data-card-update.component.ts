import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDataCard, DataCard } from 'app/shared/model/data-card.model';
import { DataCardService } from './data-card.service';
import { IDataSource } from 'app/shared/model/data-source.model';
import { DataSourceService } from 'app/entities/data-source';

@Component({
  selector: 'jhi-data-card-update',
  templateUrl: './data-card-update.component.html'
})
export class DataCardUpdateComponent implements OnInit {
  dataCard: IDataCard;
  isSaving: boolean;

  datasources: IDataSource[];

  editForm = this.fb.group({
    id: [],
    type: [null, [Validators.required]],
    status: [null, [Validators.required]],
    dataSourceId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected dataCardService: DataCardService,
    protected dataSourceService: DataSourceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dataCard }) => {
      this.updateForm(dataCard);
      this.dataCard = dataCard;
    });
    this.dataSourceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDataSource[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDataSource[]>) => response.body)
      )
      .subscribe((res: IDataSource[]) => (this.datasources = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dataCard: IDataCard) {
    this.editForm.patchValue({
      id: dataCard.id,
      type: dataCard.type,
      status: dataCard.status,
      dataSourceId: dataCard.dataSourceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dataCard = this.createFromForm();
    if (dataCard.id !== undefined) {
      this.subscribeToSaveResponse(this.dataCardService.update(dataCard));
    } else {
      this.subscribeToSaveResponse(this.dataCardService.create(dataCard));
    }
  }

  private createFromForm(): IDataCard {
    const entity = {
      ...new DataCard(),
      id: this.editForm.get(['id']).value,
      type: this.editForm.get(['type']).value,
      status: this.editForm.get(['status']).value,
      dataSourceId: this.editForm.get(['dataSourceId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataCard>>) {
    result.subscribe((res: HttpResponse<IDataCard>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackDataSourceById(index: number, item: IDataSource) {
    return item.id;
  }
}
