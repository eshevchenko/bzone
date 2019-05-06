import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { BzoneSharedModule } from 'app/shared';
import {
  FlowComponent,
  FlowDetailComponent,
  FlowUpdateComponent,
  FlowDeletePopupComponent,
  FlowDeleteDialogComponent,
  flowRoute,
  flowPopupRoute
} from './';

const ENTITY_STATES = [...flowRoute, ...flowPopupRoute];

@NgModule({
  imports: [BzoneSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [FlowComponent, FlowDetailComponent, FlowUpdateComponent, FlowDeleteDialogComponent, FlowDeletePopupComponent],
  entryComponents: [FlowComponent, FlowUpdateComponent, FlowDeleteDialogComponent, FlowDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BzoneFlowModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
