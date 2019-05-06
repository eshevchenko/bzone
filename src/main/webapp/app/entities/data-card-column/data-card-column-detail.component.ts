import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataCardColumn } from 'app/shared/model/data-card-column.model';

@Component({
  selector: 'jhi-data-card-column-detail',
  templateUrl: './data-card-column-detail.component.html'
})
export class DataCardColumnDetailComponent implements OnInit {
  dataCardColumn: IDataCardColumn;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataCardColumn }) => {
      this.dataCardColumn = dataCardColumn;
    });
  }

  previousState() {
    window.history.back();
  }
}
