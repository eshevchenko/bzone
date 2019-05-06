import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BzoneSharedModule } from 'app/shared';
import {
  ColumnPreferencesComponent,
  ColumnPreferencesDetailComponent,
  ColumnPreferencesUpdateComponent,
  ColumnPreferencesDeletePopupComponent,
  ColumnPreferencesDeleteDialogComponent,
  columnPreferencesRoute,
  columnPreferencesPopupRoute
} from './';

const ENTITY_STATES = [...columnPreferencesRoute, ...columnPreferencesPopupRoute];

@NgModule({
  imports: [BzoneSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    ColumnPreferencesComponent,
    ColumnPreferencesDetailComponent,
    ColumnPreferencesUpdateComponent,
    ColumnPreferencesDeleteDialogComponent,
    ColumnPreferencesDeletePopupComponent
  ],
  entryComponents: [
    ColumnPreferencesComponent,
    ColumnPreferencesUpdateComponent,
    ColumnPreferencesDeleteDialogComponent,
    ColumnPreferencesDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BzoneColumnPreferencesModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
