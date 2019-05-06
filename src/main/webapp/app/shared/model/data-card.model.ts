export const enum DataCardType {
  CLUSTERING = 'CLUSTERING',
  DEDUPLICATION = 'DEDUPLICATION',
  CLASSIFICATION = 'CLASSIFICATION',
  PREDICTION = 'PREDICTION',
  FRAUD_DETECTION = 'FRAUD_DETECTION'
}

export const enum DataCardStatus {
  DRAFT = 'DRAFT',
  RUNNING = 'RUNNING',
  COMPLETED = 'COMPLETED',
  ERROR = 'ERROR'
}

export interface IDataCard {
  id?: number;
  type?: DataCardType;
  status?: DataCardStatus;
  dataSourceId?: number;
}

export class DataCard implements IDataCard {
  constructor(public id?: number, public type?: DataCardType, public status?: DataCardStatus, public dataSourceId?: number) {}
}
