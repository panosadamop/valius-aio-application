export interface IExternalReports {
  id?: number;
  reportUrl?: string;
  description?: string | null;
}

export const defaultValue: Readonly<IExternalReports> = {};
