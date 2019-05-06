export const enum ColumnDataType {
  STRING = 'STRING',
  NUMERIC = 'NUMERIC',
  DATE = 'DATE',
  DICTIONARY = 'DICTIONARY'
}

export interface IDataCardColumn {
  id?: number;
  dataType?: ColumnDataType;
  dataCardId?: number;
  dataSourceColumnId?: number;
}

export class DataCardColumn implements IDataCardColumn {
  constructor(public id?: number, public dataType?: ColumnDataType, public dataCardId?: number, public dataSourceColumnId?: number) {}
}
