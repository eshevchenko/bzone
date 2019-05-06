import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataSourceColumn } from 'app/shared/model/data-source-column.model';

@Component({
  selector: 'jhi-data-source-column-detail',
  templateUrl: './data-source-column-detail.component.html'
})
export class DataSourceColumnDetailComponent implements OnInit {
  dataSourceColumn: IDataSourceColumn;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataSourceColumn }) => {
      this.dataSourceColumn = dataSourceColumn;
    });
  }

  previousState() {
    window.history.back();
  }
}
