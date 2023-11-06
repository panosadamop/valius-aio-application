import { Language } from 'app/shared/model/enumerations/language.model';

export interface IMarketAttractivenessFactorsCategory {
  id?: number;
  value?: string;
  tab?: number;
  description?: string | null;
  language?: Language;
}

export const defaultValue: Readonly<IMarketAttractivenessFactorsCategory> = {};
