export interface IFlow {
  id?: number;
  config?: string;
  context?: string;
  activeStep?: string;
  dataCardId?: number;
}

export class Flow implements IFlow {
  constructor(
    public id?: number,
    public config?: string,
    public context?: string,
    public activeStep?: string,
    public dataCardId?: number
  ) {}
}
