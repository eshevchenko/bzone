import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'data-source',
        loadChildren: './data-source/data-source.module#BzoneDataSourceModule'
      },
      {
        path: 'data-source-file',
        loadChildren: './data-source-file/data-source-file.module#BzoneDataSourceFileModule'
      },
      {
        path: 'data-card',
        loadChildren: './data-card/data-card.module#BzoneDataCardModule'
      },
      {
        path: 'data-source-column',
        loadChildren: './data-source-column/data-source-column.module#BzoneDataSourceColumnModule'
      },
      {
        path: 'column-preferences',
        loadChildren: './column-preferences/column-preferences.module#BzoneColumnPreferencesModule'
      },
      {
        path: 'data-card-column',
        loadChildren: './data-card-column/data-card-column.module#BzoneDataCardColumnModule'
      },
      {
        path: 'flow',
        loadChildren: './flow/flow.module#BzoneFlowModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BzoneEntityModule {}
