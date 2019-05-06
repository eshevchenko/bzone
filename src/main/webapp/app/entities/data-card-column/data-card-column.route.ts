import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataCardColumn } from 'app/shared/model/data-card-column.model';
import { DataCardColumnService } from './data-card-column.service';
import { DataCardColumnComponent } from './data-card-column.component';
import { DataCardColumnDetailComponent } from './data-card-column-detail.component';
import { DataCardColumnUpdateComponent } from './data-card-column-update.component';
import { DataCardColumnDeletePopupComponent } from './data-card-column-delete-dialog.component';
import { IDataCardColumn } from 'app/shared/model/data-card-column.model';

@Injectable({ providedIn: 'root' })
export class DataCardColumnResolve implements Resolve<IDataCardColumn> {
  constructor(private service: DataCardColumnService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataCardColumn> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DataCardColumn>) => response.ok),
        map((dataCardColumn: HttpResponse<DataCardColumn>) => dataCardColumn.body)
      );
    }
    return of(new DataCardColumn());
  }
}

export const dataCardColumnRoute: Routes = [
  {
    path: '',
    component: DataCardColumnComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataCardColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DataCardColumnDetailComponent,
    resolve: {
      dataCardColumn: DataCardColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataCardColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DataCardColumnUpdateComponent,
    resolve: {
      dataCardColumn: DataCardColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataCardColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DataCardColumnUpdateComponent,
    resolve: {
      dataCardColumn: DataCardColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataCardColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dataCardColumnPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DataCardColumnDeletePopupComponent,
    resolve: {
      dataCardColumn: DataCardColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataCardColumn.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
