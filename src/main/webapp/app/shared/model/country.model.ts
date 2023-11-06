import { Language } from 'app/shared/model/enumerations/language.model';

export interface ICountry {
  id?: number;
  country?: string;
  language?: Language;
}

export const defaultValue: Readonly<ICountry> = {};
