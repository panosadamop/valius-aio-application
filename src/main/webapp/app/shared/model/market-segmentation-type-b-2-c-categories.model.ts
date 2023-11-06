import { Language } from 'app/shared/model/enumerations/language.model';

export interface IMarketSegmentationTypeB2cCategories {
  id?: number;
  value?: string;
  description?: string | null;
  placeholder?: string | null;
  uniqueCharacteristic?: string;
  language?: Language;
}

export const defaultValue: Readonly<IMarketSegmentationTypeB2cCategories> = {};
