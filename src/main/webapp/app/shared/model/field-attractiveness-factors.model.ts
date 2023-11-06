import { ILevelThree } from 'app/shared/model/level-three.model';

export interface IFieldAttractivenessFactors {
  id?: number;
  attractivenessFactors?: string | null;
  jsons?: ILevelThree[] | null;
}

export const defaultValue: Readonly<IFieldAttractivenessFactors> = {};
