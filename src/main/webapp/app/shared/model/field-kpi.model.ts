import { ILevelOne } from 'app/shared/model/level-one.model';

export interface IFieldKpi {
  id?: number;
  kpis?: string | null;
  jsons?: ILevelOne[] | null;
}

export const defaultValue: Readonly<IFieldKpi> = {};
