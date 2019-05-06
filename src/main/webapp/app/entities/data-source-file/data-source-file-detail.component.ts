import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataSourceFile } from 'app/shared/model/data-source-file.model';

@Component({
  selector: 'jhi-data-source-file-detail',
  templateUrl: './data-source-file-detail.component.html'
})
export class DataSourceFileDetailComponent implements OnInit {
  dataSourceFile: IDataSourceFile;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataSourceFile }) => {
      this.dataSourceFile = dataSourceFile;
    });
  }

  previousState() {
    window.history.back();
  }
}
