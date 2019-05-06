import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BzoneSharedModule } from 'app/shared';
import {
  DataSourceColumnComponent,
  DataSourceColumnDetailComponent,
  DataSourceColumnUpdateComponent,
  DataSourceColumnDeletePopupComponent,
  DataSourceColumnDeleteDialogComponent,
  dataSourceColumnRoute,
  dataSourceColumnPopupRoute
} from './';

const ENTITY_STATES = [...dataSourceColumnRoute, ...dataSourceColumnPopupRoute];

@NgModule({
  imports: [BzoneSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DataSourceColumnComponent,
    DataSourceColumnDetailComponent,
    DataSourceColumnUpdateComponent,
    DataSourceColumnDeleteDialogComponent,
    DataSourceColumnDeletePopupComponent
  ],
  entryComponents: [
    DataSourceColumnComponent,
    DataSourceColumnUpdateComponent,
    DataSourceColumnDeleteDialogComponent,
    DataSourceColumnDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BzoneDataSourceColumnModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
