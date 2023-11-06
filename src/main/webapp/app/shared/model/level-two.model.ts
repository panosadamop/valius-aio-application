import { IUser } from 'app/shared/model/user.model';
import { IFieldBuyingCriteria } from 'app/shared/model/field-buying-criteria.model';

export interface ILevelTwo {
  id?: number;
  identifier?: string;
  targetMarket?: string;
  currentMarketSegmentation?: string;
  segmentName?: string;
  marketSegmentationType?: string | null;
  uniqueCharacteristic?: string | null;
  segmentDescription?: string;
  buyingCriteriaCategory?: string;
  competitorProductName?: string;
  competitorCompanyName?: string;
  competitorBrandName?: string;
  competitorProductDescription?: string;
  competitorMaturityPhase?: string;
  competitorCompetitivePosition?: string;
  user?: IUser | null;
  buyingCriteria?: IFieldBuyingCriteria | null;
}

export const defaultValue: Readonly<ILevelTwo> = {};
