import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { DataSourceFile } from 'app/shared/model/data-source-file.model';
import { DataSourceFileService } from './data-source-file.service';
import { DataSourceFileComponent } from './data-source-file.component';
import { DataSourceFileDetailComponent } from './data-source-file-detail.component';
import { DataSourceFileUpdateComponent } from './data-source-file-update.component';
import { DataSourceFileDeletePopupComponent } from './data-source-file-delete-dialog.component';
import { IDataSourceFile } from 'app/shared/model/data-source-file.model';

@Injectable({ providedIn: 'root' })
export class DataSourceFileResolve implements Resolve<IDataSourceFile> {
  constructor(private service: DataSourceFileService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IDataSourceFile> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<DataSourceFile>) => response.ok),
        map((dataSourceFile: HttpResponse<DataSourceFile>) => dataSourceFile.body)
      );
    }
    return of(new DataSourceFile());
  }
}

export const dataSourceFileRoute: Routes = [
  {
    path: '',
    component: DataSourceFileComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSourceFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DataSourceFileDetailComponent,
    resolve: {
      dataSourceFile: DataSourceFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSourceFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DataSourceFileUpdateComponent,
    resolve: {
      dataSourceFile: DataSourceFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSourceFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DataSourceFileUpdateComponent,
    resolve: {
      dataSourceFile: DataSourceFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSourceFile.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const dataSourceFilePopupRoute: Routes = [
  {
    path: ':id/delete',
    component: DataSourceFileDeletePopupComponent,
    resolve: {
      dataSourceFile: DataSourceFileResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.dataSourceFile.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
