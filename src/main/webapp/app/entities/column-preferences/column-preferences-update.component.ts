import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IColumnPreferences, ColumnPreferences } from 'app/shared/model/column-preferences.model';
import { ColumnPreferencesService } from './column-preferences.service';

@Component({
  selector: 'jhi-column-preferences-update',
  templateUrl: './column-preferences-update.component.html'
})
export class ColumnPreferencesUpdateComponent implements OnInit {
  columnPreferences: IColumnPreferences;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    key: [null, [Validators.required]],
    value: [null, [Validators.required]]
  });

  constructor(
    protected columnPreferencesService: ColumnPreferencesService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ columnPreferences }) => {
      this.updateForm(columnPreferences);
      this.columnPreferences = columnPreferences;
    });
  }

  updateForm(columnPreferences: IColumnPreferences) {
    this.editForm.patchValue({
      id: columnPreferences.id,
      key: columnPreferences.key,
      value: columnPreferences.value
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const columnPreferences = this.createFromForm();
    if (columnPreferences.id !== undefined) {
      this.subscribeToSaveResponse(this.columnPreferencesService.update(columnPreferences));
    } else {
      this.subscribeToSaveResponse(this.columnPreferencesService.create(columnPreferences));
    }
  }

  private createFromForm(): IColumnPreferences {
    const entity = {
      ...new ColumnPreferences(),
      id: this.editForm.get(['id']).value,
      key: this.editForm.get(['key']).value,
      value: this.editForm.get(['value']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IColumnPreferences>>) {
    result.subscribe((res: HttpResponse<IColumnPreferences>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
