import { ILevelOne } from 'app/shared/model/level-one.model';

export interface IFieldCompanyObjectives {
  id?: number;
  companyObjectives?: string | null;
  jsons?: ILevelOne[] | null;
}

export const defaultValue: Readonly<IFieldCompanyObjectives> = {};
