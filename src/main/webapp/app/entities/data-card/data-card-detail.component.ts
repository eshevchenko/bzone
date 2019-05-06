import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDataCard } from 'app/shared/model/data-card.model';

@Component({
  selector: 'jhi-data-card-detail',
  templateUrl: './data-card-detail.component.html'
})
export class DataCardDetailComponent implements OnInit {
  dataCard: IDataCard;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ dataCard }) => {
      this.dataCard = dataCard;
    });
  }

  previousState() {
    window.history.back();
  }
}
