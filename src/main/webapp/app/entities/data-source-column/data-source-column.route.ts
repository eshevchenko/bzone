import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataSourceColumn } from 'app/shared/model/data-source-column.model';
import { DataSourceColumnService } from './data-source-column.service';
import { DataSourceColumnComponent } from './data-source-column.component';
import { DataSourceColumnDetailComponent } from './data-source-column-detail.component';
import { DataSourceColumnUpdateComponent } from './data-source-column-update.component';
import { DataSourceColumnDeletePopupComponent } from './data-source-column-delete-dialog.component';
import { IDataSourceColumn } from 'app/shared/model/data-source-column.model';

@Injectable({ providedIn: 'root' })
export class DataSourceColumnResolve implements Resolve<IDataSourceColumn> {
  constructor(private service: DataSourceColumnService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataSourceColumn> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DataSourceColumn>) => response.ok),
        map((dataSourceColumn: HttpResponse<DataSourceColumn>) => dataSourceColumn.body)
      );
    }
    return of(new DataSourceColumn());
  }
}

export const dataSourceColumnRoute: Routes = [
  {
    path: '',
    component: DataSourceColumnComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSourceColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DataSourceColumnDetailComponent,
    resolve: {
      dataSourceColumn: DataSourceColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSourceColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DataSourceColumnUpdateComponent,
    resolve: {
      dataSourceColumn: DataSourceColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSourceColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DataSourceColumnUpdateComponent,
    resolve: {
      dataSourceColumn: DataSourceColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSourceColumn.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dataSourceColumnPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DataSourceColumnDeletePopupComponent,
    resolve: {
      dataSourceColumn: DataSourceColumnResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSourceColumn.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
