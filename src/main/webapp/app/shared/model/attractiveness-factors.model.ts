import { Language } from 'app/shared/model/enumerations/language.model';

export interface IAttractivenessFactors {
  id?: number;
  value?: string;
  description?: string | null;
  language?: Language;
}

export const defaultValue: Readonly<IAttractivenessFactors> = {};
