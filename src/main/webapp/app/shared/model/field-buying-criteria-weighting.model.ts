import { ISurvey } from 'app/shared/model/survey.model';

export interface IFieldBuyingCriteriaWeighting {
  id?: number;
  buyingCriteriaWeighting?: string | null;
  jsons?: ISurvey[] | null;
}

export const defaultValue: Readonly<IFieldBuyingCriteriaWeighting> = {};
