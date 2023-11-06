import { ILevelOne } from 'app/shared/model/level-one.model';

export interface IFieldProductype {
  id?: number;
  productType?: string | null;
  jsons?: ILevelOne[] | null;
}

export const defaultValue: Readonly<IFieldProductype> = {};
