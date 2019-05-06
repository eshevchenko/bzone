export const enum ColumnDataType {
  STRING = 'STRING',
  NUMERIC = 'NUMERIC',
  DATE = 'DATE',
  DICTIONARY = 'DICTIONARY'
}

export interface IDataSourceColumn {
  id?: number;
  name?: string;
  dataType?: ColumnDataType;
  dataSourceId?: number;
}

export class DataSourceColumn implements IDataSourceColumn {
  constructor(public id?: number, public name?: string, public dataType?: ColumnDataType, public dataSourceId?: number) {}
}
