import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataSource } from 'app/shared/model/data-source.model';
import { DataSourceService } from './data-source.service';
import { DataSourceComponent } from './data-source.component';
import { DataSourceDetailComponent } from './data-source-detail.component';
import { DataSourceUpdateComponent } from './data-source-update.component';
import { DataSourceDeletePopupComponent } from './data-source-delete-dialog.component';
import { IDataSource } from 'app/shared/model/data-source.model';

@Injectable({ providedIn: 'root' })
export class DataSourceResolve implements Resolve<IDataSource> {
  constructor(private service: DataSourceService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataSource> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DataSource>) => response.ok),
        map((dataSource: HttpResponse<DataSource>) => dataSource.body)
      );
    }
    return of(new DataSource());
  }
}

export const dataSourceRoute: Routes = [
  {
    path: '',
    component: DataSourceComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DataSourceDetailComponent,
    resolve: {
      dataSource: DataSourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DataSourceUpdateComponent,
    resolve: {
      dataSource: DataSourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSource.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DataSourceUpdateComponent,
    resolve: {
      dataSource: DataSourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSource.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dataSourcePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DataSourceDeletePopupComponent,
    resolve: {
      dataSource: DataSourceResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSource.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
