import { Language } from 'app/shared/model/enumerations/language.model';

export interface IInfoPageCategory {
  id?: number;
  value?: string;
  language?: Language;
}

export const defaultValue: Readonly<IInfoPageCategory> = {};
