import { IUser } from 'app/shared/model/user.model';

export interface ILevelFour {
  id?: number;
  identifier?: string;
  criticalSuccessFactors?: string;
  populationSize?: string;
  statisticalError?: string;
  confidenceLevel?: string;
  requiredSampleSize?: string;
  estimatedResponseRate?: string;
  surveyParticipantsNumber?: number;
  user?: IUser | null;
}

export const defaultValue: Readonly<ILevelFour> = {};
