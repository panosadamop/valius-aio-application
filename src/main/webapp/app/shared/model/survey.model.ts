import { IUser } from 'app/shared/model/user.model';
import { IFieldBuyingCriteriaWeighting } from 'app/shared/model/field-buying-criteria-weighting.model';
import { IFieldPreferredPurchaseChannel } from 'app/shared/model/field-preferred-purchase-channel.model';
import { IFieldPreferredCommunicationChannel } from 'app/shared/model/field-preferred-communication-channel.model';

export interface ISurvey {
  id?: number;
  consumerAssessedBrands?: string;
  criticalSuccessFactors?: string;
  additionalBuyingCriteria?: string;
  consumerSegmentGroup?: string;
  segmentCsf?: string;
  gender?: string;
  ageGroup?: string;
  location?: string;
  education?: string;
  occupation?: string;
  netPromoterScore?: string;
  user?: IUser | null;
  buyingCriteriaWeighting?: IFieldBuyingCriteriaWeighting | null;
  preferredPurchaseChannel?: IFieldPreferredPurchaseChannel | null;
  preferredCommunicationChannel?: IFieldPreferredCommunicationChannel | null;
}

export const defaultValue: Readonly<ISurvey> = {};
