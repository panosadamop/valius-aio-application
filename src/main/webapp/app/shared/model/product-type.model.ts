import { Language } from 'app/shared/model/enumerations/language.model';

export interface IProductType {
  id?: number;
  value?: string;
  checkBoxValue?: boolean;
  description?: string | null;
  language?: Language;
}

export const defaultValue: Readonly<IProductType> = {
  checkBoxValue: false,
};
