import { ILevelTwo } from 'app/shared/model/level-two.model';

export interface IFieldBuyingCriteria {
  id?: number;
  buyingCriteria?: string | null;
  jsons?: ILevelTwo[] | null;
}

export const defaultValue: Readonly<IFieldBuyingCriteria> = {};
