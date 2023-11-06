export interface IPyramidData {
  id?: number;
  identifier?: string;
  category?: number;
  value?: string;
  order?: number;
  imgContentType?: string | null;
  img?: string | null;
}

export const defaultValue: Readonly<IPyramidData> = {};
