import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BzoneSharedModule } from 'app/shared';
import {
  DataCardComponent,
  DataCardDetailComponent,
  DataCardUpdateComponent,
  DataCardDeletePopupComponent,
  DataCardDeleteDialogComponent,
  dataCardRoute,
  dataCardPopupRoute
} from './';

const ENTITY_STATES = [...dataCardRoute, ...dataCardPopupRoute];

@NgModule({
  imports: [BzoneSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    DataCardComponent,
    DataCardDetailComponent,
    DataCardUpdateComponent,
    DataCardDeleteDialogComponent,
    DataCardDeletePopupComponent
  ],
  entryComponents: [DataCardComponent, DataCardUpdateComponent, DataCardDeleteDialogComponent, DataCardDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BzoneDataCardModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
