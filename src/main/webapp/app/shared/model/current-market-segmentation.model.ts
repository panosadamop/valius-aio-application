import { Language } from 'app/shared/model/enumerations/language.model';

export interface ICurrentMarketSegmentation {
  id?: number;
  value?: string;
  description?: string | null;
  language?: Language;
}

export const defaultValue: Readonly<ICurrentMarketSegmentation> = {};
