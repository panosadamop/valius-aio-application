import { Language } from 'app/shared/model/enumerations/language.model';

export interface ICompanyObjectives {
  id?: number;
  value?: string;
  placeholder?: string | null;
  description?: string | null;
  language?: Language;
}

export const defaultValue: Readonly<ICompanyObjectives> = {};
