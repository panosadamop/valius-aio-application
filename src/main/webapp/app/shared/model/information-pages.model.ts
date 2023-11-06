export interface IInformationPages {
  id?: number;
  title?: string;
  subTitle?: string | null;
  description?: string;
}

export const defaultValue: Readonly<IInformationPages> = {};
