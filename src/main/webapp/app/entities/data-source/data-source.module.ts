import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BzoneSharedModule } from 'app/shared';
import {
  DataSourceComponent,
  DataSourceDetailComponent,
  DataSourceUpdateComponent,
  DataSourceDeletePopupComponent,
  DataSourceDeleteDialogComponent,
  dataSourceRoute,
  dataSourcePopupRoute
} from './';

const ENTITY_STATES = [...dataSourceRoute, ...dataSourcePopupRoute];

@NgModule({
  imports: [BzoneSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DataSourceComponent,
    DataSourceDetailComponent,
    DataSourceUpdateComponent,
    DataSourceDeleteDialogComponent,
    DataSourceDeletePopupComponent
  ],
  entryComponents: [DataSourceComponent, DataSourceUpdateComponent, DataSourceDeleteDialogComponent, DataSourceDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BzoneDataSourceModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
