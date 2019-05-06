export interface IDataSource {
  id?: number;
}

export class DataSource implements IDataSource {
  constructor(public id?: number) {}
}
