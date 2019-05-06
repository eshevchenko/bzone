import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BzoneSharedModule } from 'app/shared';
import {
  DataSourceFileComponent,
  DataSourceFileDetailComponent,
  DataSourceFileUpdateComponent,
  DataSourceFileDeletePopupComponent,
  DataSourceFileDeleteDialogComponent,
  dataSourceFileRoute,
  dataSourceFilePopupRoute
} from './';

const ENTITY_STATES = [...dataSourceFileRoute, ...dataSourceFilePopupRoute];

@NgModule({
  imports: [BzoneSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DataSourceFileComponent,
    DataSourceFileDetailComponent,
    DataSourceFileUpdateComponent,
    DataSourceFileDeleteDialogComponent,
    DataSourceFileDeletePopupComponent
  ],
  entryComponents: [
    DataSourceFileComponent,
    DataSourceFileUpdateComponent,
    DataSourceFileDeleteDialogComponent,
    DataSourceFileDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BzoneDataSourceFileModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
