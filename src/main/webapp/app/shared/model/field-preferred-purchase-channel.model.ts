import { ISurvey } from 'app/shared/model/survey.model';

export interface IFieldPreferredPurchaseChannel {
  id?: number;
  preferredPurchaseChannel?: string | null;
  jsons?: ISurvey[] | null;
}

export const defaultValue: Readonly<IFieldPreferredPurchaseChannel> = {};
