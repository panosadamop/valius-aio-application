import { Language } from 'app/shared/model/enumerations/language.model';

export interface IRequiredSampleSize {
  id?: number;
  value?: string;
  language?: Language;
}

export const defaultValue: Readonly<IRequiredSampleSize> = {};
