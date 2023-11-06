import { Language } from 'app/shared/model/enumerations/language.model';

export interface ISegmentUniqueCharacteristic {
  id?: number;
  value?: string;
  category?: string;
  description?: string | null;
  language?: Language;
}

export const defaultValue: Readonly<ISegmentUniqueCharacteristic> = {};
