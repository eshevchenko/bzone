import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDataCardColumn, DataCardColumn } from 'app/shared/model/data-card-column.model';
import { DataCardColumnService } from './data-card-column.service';
import { IDataCard } from 'app/shared/model/data-card.model';
import { DataCardService } from 'app/entities/data-card';
import { IDataSourceColumn } from 'app/shared/model/data-source-column.model';
import { DataSourceColumnService } from 'app/entities/data-source-column';

@Component({
  selector: 'jhi-data-card-column-update',
  templateUrl: './data-card-column-update.component.html'
})
export class DataCardColumnUpdateComponent implements OnInit {
  dataCardColumn: IDataCardColumn;
  isSaving: boolean;

  datacards: IDataCard[];

  datasourcecolumns: IDataSourceColumn[];

  editForm = this.fb.group({
    id: [],
    dataType: [null, [Validators.required]],
    dataCardId: [null, Validators.required],
    dataSourceColumnId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected dataCardColumnService: DataCardColumnService,
    protected dataCardService: DataCardService,
    protected dataSourceColumnService: DataSourceColumnService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dataCardColumn }) => {
      this.updateForm(dataCardColumn);
      this.dataCardColumn = dataCardColumn;
    });
    this.dataCardService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDataCard[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDataCard[]>) => response.body)
      )
      .subscribe((res: IDataCard[]) => (this.datacards = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.dataSourceColumnService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDataSourceColumn[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDataSourceColumn[]>) => response.body)
      )
      .subscribe((res: IDataSourceColumn[]) => (this.datasourcecolumns = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dataCardColumn: IDataCardColumn) {
    this.editForm.patchValue({
      id: dataCardColumn.id,
      dataType: dataCardColumn.dataType,
      dataCardId: dataCardColumn.dataCardId,
      dataSourceColumnId: dataCardColumn.dataSourceColumnId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dataCardColumn = this.createFromForm();
    if (dataCardColumn.id !== undefined) {
      this.subscribeToSaveResponse(this.dataCardColumnService.update(dataCardColumn));
    } else {
      this.subscribeToSaveResponse(this.dataCardColumnService.create(dataCardColumn));
    }
  }

  private createFromForm(): IDataCardColumn {
    const entity = {
      ...new DataCardColumn(),
      id: this.editForm.get(['id']).value,
      dataType: this.editForm.get(['dataType']).value,
      dataCardId: this.editForm.get(['dataCardId']).value,
      dataSourceColumnId: this.editForm.get(['dataSourceColumnId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataCardColumn>>) {
    result.subscribe((res: HttpResponse<IDataCardColumn>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackDataSourceColumnById(index: number, item: IDataSourceColumn) {
    return item.id;
  }
}
