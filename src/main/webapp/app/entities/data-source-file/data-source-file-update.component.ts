import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDataSourceFile, DataSourceFile } from 'app/shared/model/data-source-file.model';
import { DataSourceFileService } from './data-source-file.service';
import { IDataSource } from 'app/shared/model/data-source.model';
import { DataSourceService } from 'app/entities/data-source';

@Component({
  selector: 'jhi-data-source-file-update',
  templateUrl: './data-source-file-update.component.html'
})
export class DataSourceFileUpdateComponent implements OnInit {
  dataSourceFile: IDataSourceFile;
  isSaving: boolean;

  datasources: IDataSource[];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    path: [null, [Validators.required]],
    size: [null, [Validators.required]],
    dataSourceId: [null, Validators.required]
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected dataSourceFileService: DataSourceFileService,
    protected dataSourceService: DataSourceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dataSourceFile }) => {
      this.updateForm(dataSourceFile);
      this.dataSourceFile = dataSourceFile;
    });
    this.dataSourceService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IDataSource[]>) => mayBeOk.ok),
        map((response: HttpResponse<IDataSource[]>) => response.body)
      )
      .subscribe((res: IDataSource[]) => (this.datasources = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(dataSourceFile: IDataSourceFile) {
    this.editForm.patchValue({
      id: dataSourceFile.id,
      name: dataSourceFile.name,
      path: dataSourceFile.path,
      size: dataSourceFile.size,
      dataSourceId: dataSourceFile.dataSourceId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dataSourceFile = this.createFromForm();
    if (dataSourceFile.id !== undefined) {
      this.subscribeToSaveResponse(this.dataSourceFileService.update(dataSourceFile));
    } else {
      this.subscribeToSaveResponse(this.dataSourceFileService.create(dataSourceFile));
    }
  }

  private createFromForm(): IDataSourceFile {
    const entity = {
      ...new DataSourceFile(),
      id: this.editForm.get(['id']).value,
      name: this.editForm.get(['name']).value,
      path: this.editForm.get(['path']).value,
      size: this.editForm.get(['size']).value,
      dataSourceId: this.editForm.get(['dataSourceId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataSourceFile>>) {
    result.subscribe((res: HttpResponse<IDataSourceFile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
