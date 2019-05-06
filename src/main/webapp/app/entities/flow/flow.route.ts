import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Flow } from 'app/shared/model/flow.model';
import { FlowService } from './flow.service';
import { FlowComponent } from './flow.component';
import { FlowDetailComponent } from './flow-detail.component';
import { FlowUpdateComponent } from './flow-update.component';
import { FlowDeletePopupComponent } from './flow-delete-dialog.component';
import { IFlow } from 'app/shared/model/flow.model';

@Injectable({ providedIn: 'root' })
export class FlowResolve implements Resolve<IFlow> {
  constructor(private service: FlowService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IFlow> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Flow>) => response.ok),
        map((flow: HttpResponse<Flow>) => flow.body)
      );
    }
    return of(new Flow());
  }
}

export const flowRoute: Routes = [
  {
    path: '',
    component: FlowComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.flow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FlowDetailComponent,
    resolve: {
      flow: FlowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.flow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FlowUpdateComponent,
    resolve: {
      flow: FlowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.flow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FlowUpdateComponent,
    resolve: {
      flow: FlowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.flow.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const flowPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: FlowDeletePopupComponent,
    resolve: {
      flow: FlowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'bzoneApp.flow.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];