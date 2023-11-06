import { Language } from 'app/shared/model/enumerations/language.model';

export interface IMarketSegmentationTypeB2cAlt {
  id?: number;
  value?: string;
  description?: string | null;
  language?: Language;
}

export const defaultValue: Readonly<IMarketSegmentationTypeB2cAlt> = {};
