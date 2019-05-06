import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataCard } from 'app/shared/model/data-card.model';
import { DataCardService } from './data-card.service';
import { DataCardComponent } from './data-card.component';
import { DataCardDetailComponent } from './data-card-detail.component';
import { DataCardUpdateComponent } from './data-card-update.component';
import { DataCardDeletePopupComponent } from './data-card-delete-dialog.component';
import { IDataCard } from 'app/shared/model/data-card.model';

@Injectable({ providedIn: 'root' })
export class DataCardResolve implements Resolve<IDataCard> {
  constructor(private service: DataCardService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataCard> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DataCard>) => response.ok),
        map((dataCard: HttpResponse<DataCard>) => dataCard.body)
      );
    }
    return of(new DataCard());
  }
}

export const dataCardRoute: Routes = [
  {
    path: '',
    component: DataCardComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataCard.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DataCardDetailComponent,
    resolve: {
      dataCard: DataCardResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataCard.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DataCardUpdateComponent,
    resolve: {
      dataCard: DataCardResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataCard.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DataCardUpdateComponent,
    resolve: {
      dataCard: DataCardResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataCard.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dataCardPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DataCardDeletePopupComponent,
    resolve: {
      dataCard: DataCardResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataCard.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
