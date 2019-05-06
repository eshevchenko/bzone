export interface IColumnPreferences {
  id?: number;
  key?: string;
  value?: string;
}

export class ColumnPreferences implements IColumnPreferences {
  constructor(public id?: number, public key?: string, public value?: string) {}
}
