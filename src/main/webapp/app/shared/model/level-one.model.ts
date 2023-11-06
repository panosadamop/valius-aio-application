import { IUser } from 'app/shared/model/user.model';
import { IFieldCompanyObjectives } from 'app/shared/model/field-company-objectives.model';
import { IFieldKpi } from 'app/shared/model/field-kpi.model';
import { IFieldProductype } from 'app/shared/model/field-productype.model';

export interface ILevelOne {
  id?: number;
  identifier?: string;
  companyName?: string;
  companyLogoContentType?: string | null;
  companyLogo?: string | null;
  brandName?: string | null;
  productLogoContentType?: string | null;
  productLogo?: string | null;
  industry?: string | null;
  organizationType?: string | null;
  productsServices?: string;
  territory?: string | null;
  noEmployees?: string | null;
  revenues?: string | null;
  mission?: string;
  vision?: string;
  companyValues?: string;
  strategicFocus?: string | null;
  marketingBudget?: string | null;
  productDescription?: string | null;
  maturityPhase?: string | null;
  competitivePosition?: string | null;
  targetAudienceDescription?: string;
  potentialCustomersGroups?: string;
  strengths?: string;
  weaknesses?: string;
  opportunities?: string;
  threats?: string;
  user?: IUser | null;
  companyObjectives?: IFieldCompanyObjectives | null;
  kpis?: IFieldKpi | null;
  productType?: IFieldProductype | null;
}

export const defaultValue: Readonly<ILevelOne> = {};
