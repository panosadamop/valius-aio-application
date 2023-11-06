import { IUser } from 'app/shared/model/user.model';
import { IFieldAttractivenessFactors } from 'app/shared/model/field-attractiveness-factors.model';

export interface ILevelThree {
  id?: number;
  identifier?: string;
  mafCategory?: string;
  weightingMaf?: string;
  lowAttractivenessRangeMaf?: string;
  mediumAttractivenessRangeMaf?: string;
  highAttractivenessRangeMaf?: string;
  segmentScoreMaf?: string | null;
  user?: IUser | null;
  attractivenessFactors?: IFieldAttractivenessFactors | null;
}

export const defaultValue: Readonly<ILevelThree> = {};
