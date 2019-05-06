import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ColumnPreferences } from 'app/shared/model/column-preferences.model';
import { ColumnPreferencesService } from './column-preferences.service';
import { ColumnPreferencesComponent } from './column-preferences.component';
import { ColumnPreferencesDetailComponent } from './column-preferences-detail.component';
import { ColumnPreferencesUpdateComponent } from './column-preferences-update.component';
import { ColumnPreferencesDeletePopupComponent } from './column-preferences-delete-dialog.component';
import { IColumnPreferences } from 'app/shared/model/column-preferences.model';

@Injectable({ providedIn: 'root' })
export class ColumnPreferencesResolve implements Resolve<IColumnPreferences> {
  constructor(private service: ColumnPreferencesService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IColumnPreferences> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<ColumnPreferences>) => response.ok),
        map((columnPreferences: HttpResponse<ColumnPreferences>) => columnPreferences.body)
      );
    }
    return of(new ColumnPreferences());
  }
}

export const columnPreferencesRoute: Routes = [
  {
    path: '',
    component: ColumnPreferencesComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.columnPreferences.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ColumnPreferencesDetailComponent,
    resolve: {
      columnPreferences: ColumnPreferencesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.columnPreferences.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ColumnPreferencesUpdateComponent,
    resolve: {
      columnPreferences: ColumnPreferencesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.columnPreferences.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ColumnPreferencesUpdateComponent,
    resolve: {
      columnPreferences: ColumnPreferencesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.columnPreferences.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const columnPreferencesPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: ColumnPreferencesDeletePopupComponent,
    resolve: {
      columnPreferences: ColumnPreferencesResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.columnPreferences.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
