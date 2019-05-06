export interface IDataSourceFile {
  id?: number;
  name?: string;
  path?: string;
  size?: number;
  dataSourceId?: number;
}

export class DataSourceFile implements IDataSourceFile {
  constructor(public id?: number, public name?: string, public path?: string, public size?: number, public dataSourceId?: number) {}
}
