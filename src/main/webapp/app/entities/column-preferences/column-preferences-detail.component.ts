import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IColumnPreferences } from 'app/shared/model/column-preferences.model';

@Component({
  selector: 'jhi-column-preferences-detail',
  templateUrl: './column-preferences-detail.component.html'
})
export class ColumnPreferencesDetailComponent implements OnInit {
  columnPreferences: IColumnPreferences;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ columnPreferences }) => {
      this.columnPreferences = columnPreferences;
    });
  }

  previousState() {
    window.history.back();
  }
}
