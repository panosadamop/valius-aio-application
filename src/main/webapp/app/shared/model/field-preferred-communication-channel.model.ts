import { ISurvey } from 'app/shared/model/survey.model';

export interface IFieldPreferredCommunicationChannel {
  id?: number;
  preferredCommunicationChannel?: string | null;
  jsons?: ISurvey[] | null;
}

export const defaultValue: Readonly<IFieldPreferredCommunicationChannel> = {};
