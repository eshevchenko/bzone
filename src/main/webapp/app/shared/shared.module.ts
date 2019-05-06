import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { BzoneSharedLibsModule, BzoneSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [BzoneSharedLibsModule, BzoneSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [BzoneSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BzoneSharedModule {
  static forRoot() {
    return {
      ngModule: BzoneSharedModule
    };
  }
}
