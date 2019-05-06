import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BzoneSharedModule } from 'app/shared';
import {
  DataCardColumnComponent,
  DataCardColumnDetailComponent,
  DataCardColumnUpdateComponent,
  DataCardColumnDeletePopupComponent,
  DataCardColumnDeleteDialogComponent,
  dataCardColumnRoute,
  dataCardColumnPopupRoute
} from './';

const ENTITY_STATES = [...dataCardColumnRoute, ...dataCardColumnPopupRoute];

@NgModule({
  imports: [BzoneSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DataCardColumnComponent,
    DataCardColumnDetailComponent,
    DataCardColumnUpdateComponent,
    DataCardColumnDeleteDialogComponent,
    DataCardColumnDeletePopupComponent
  ],
  entryComponents: [
    DataCardColumnComponent,
    DataCardColumnUpdateComponent,
    DataCardColumnDeleteDialogComponent,
    DataCardColumnDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BzoneDataCardColumnModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
