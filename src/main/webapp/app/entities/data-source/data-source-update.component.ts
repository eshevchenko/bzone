import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDataSource, DataSource } from 'app/shared/model/data-source.model';
import { DataSourceService } from './data-source.service';

@Component({
  selector: 'jhi-data-source-update',
  templateUrl: './data-source-update.component.html'
})
export class DataSourceUpdateComponent implements OnInit {
  dataSource: IDataSource;
  isSaving: boolean;

  editForm = this.fb.group({
    id: []
  });

  constructor(protected dataSourceService: DataSourceService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dataSource }) => {
      this.updateForm(dataSource);
      this.dataSource = dataSource;
    });
  }

  updateForm(dataSource: IDataSource) {
    this.editForm.patchValue({
      id: dataSource.id
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dataSource = this.createFromForm();
    if (dataSource.id !== undefined) {
      this.subscribeToSaveResponse(this.dataSourceService.update(dataSource));
    } else {
      this.subscribeToSaveResponse(this.dataSourceService.create(dataSource));
    }
  }

  private createFromForm(): IDataSource {
    const entity = {
      ...new DataSource(),
      id: this.editForm.get(['id']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDataSource>>) {
    result.subscribe((res: HttpResponse<IDataSource>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
