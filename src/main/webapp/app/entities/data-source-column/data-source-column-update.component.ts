import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDataSourceColumn, DataSourceColumn } from 'app/shared/model/data-source-column.model';
import { DataSourceColumnService } from './data-source-column.service';
import { IDataSource } from 'app/shared/model/data-source.model';
import { DataSourceService } from 'app/entities/data-source';

@Component({
  selector: 'jhi-data-source-column-update',
  templateUrl: './data-source-column-update.component.html'
})
export class DataSourceColumnUpdateComponent implements OnInit {
  dataSourceColumn: IDataSourceColumn;
  isSaving: boolean;

  datasources: IDataSource[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    dataType: [null, [Validators.required]],
    dataSourceId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected dataSourceColumnService: DataSourceColumnService,
    protected dataSourceService: DataSourceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dataSourceColumn }) => {
      this.updateForm(dataSourceColumn);
      this.dataSourceColumn = dataSourceColumn;
    });
    this.dataSourceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDataSource[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDataSource[]>) => response.body)
      )
      .subscribe((res: IDataSource[]) => (this.datasources = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dataSourceColumn: IDataSourceColumn) {
    this.editForm.patchValue({
      id: dataSourceColumn.id,
      name: dataSourceColumn.name,
      dataType: dataSourceColumn.dataType,
      dataSourceId: dataSourceColumn.dataSourceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dataSourceColumn = this.createFromForm();
    if (dataSourceColumn.id !== undefined) {
      this.subscribeToSaveResponse(this.dataSourceColumnService.update(dataSourceColumn));
    } else {
      this.subscribeToSaveResponse(this.dataSourceColumnService.create(dataSourceColumn));
    }
  }

  private createFromForm(): IDataSourceColumn {
    const entity = {
      ...new DataSourceColumn(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      dataType: this.editForm.get(['dataType']).value,
      dataSourceId: this.editForm.get(['dataSourceId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataSourceColumn>>) {
    result.subscribe((res: HttpResponse<IDataSourceColumn>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
