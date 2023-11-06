import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LevelOne from './level-one';
import LevelTwo from './level-two';
import LevelThree from './level-three';
import LevelFour from './level-four';
import Survey from './survey';
import PyramidData from './pyramid-data';
import ExternalReports from './external-reports';
import InternalReports from './internal-reports';
import InformationPages from './information-pages';
import FieldCompanyObjectives from './field-company-objectives';
import FieldKpi from './field-kpi';
import FieldProductype from './field-productype';
import FieldBuyingCriteria from './field-buying-criteria';
import FieldAttractivenessFactors from './field-attractiveness-factors';
import FieldBuyingCriteriaWeighting from './field-buying-criteria-weighting';
import FieldPreferredPurchaseChannel from './field-preferred-purchase-channel';
import FieldPreferredCommunicationChannel from './field-preferred-communication-channel';
import InfoPageCategory from './info-page-category';
import RequiredSampleSize from './required-sample-size';
import Territory from './territory';
import Industry from './industry';
import Country from './country';
import TargetMarket from './target-market';
import OrganisationType from './organisation-type';
import NoOfEmployees from './no-of-employees';
import Revenues from './revenues';
import CompanyObjectives from './company-objectives';
import StrategicFocus from './strategic-focus';
import Kpis from './kpis';
import MarketingBudget from './marketing-budget';
import MaturityPhase from './maturity-phase';
import CompetitivePosition from './competitive-position';
import ProductType from './product-type';
import CurrentMarketSegmentation from './current-market-segmentation';
import MarketSegmentationTypeB2b from './market-segmentation-type-b-2-b';
import MarketSegmentationTypeB2bAlt from './market-segmentation-type-b-2-b-alt';
import MarketSegmentationTypeB2bCategories from './market-segmentation-type-b-2-b-categories';
import MarketSegmentationTypeB2c from './market-segmentation-type-b-2-c';
import MarketSegmentationTypeB2cAlt from './market-segmentation-type-b-2-c-alt';
import MarketSegmentationTypeB2cCategories from './market-segmentation-type-b-2-c-categories';
import BuyingCriteria from './buying-criteria';
import BuyingCriteriaCategory from './buying-criteria-category';
import MarketSegmentationType from './market-segmentation-type';
import SegmentUniqueCharacteristic from './segment-unique-characteristic';
import CoreProductElements from './core-product-elements';
import RelatedServiceElements from './related-service-elements';
import IntangibleElements from './intangible-elements';
import CompetitorMaturityPhase from './competitor-maturity-phase';
import CompetitorCompetitivePosition from './competitor-competitive-position';
import EconomicFactors from './economic-factors';
import CompetitiveFactors from './competitive-factors';
import SocialFactors from './social-factors';
import PopulationSize from './population-size';
import StatisticalError from './statistical-error';
import ConfidenceLevel from './confidence-level';
import BuyingCriteriaWeighting from './buying-criteria-weighting';
import AgeGroup from './age-group';
import Occupation from './occupation';
import PreferredCommunicationChannel from './preferred-communication-channel';
import Location from './location';
import Education from './education';
import PreferredPurchaseChannel from './preferred-purchase-channel';
import NetPromoterScore from './net-promoter-score';
import MarketAttractivenessFactorsCategory from './market-attractiveness-factors-category';
import SegmentScoreMAF from './segment-score-maf';
import AttractivenessFactors from './attractiveness-factors';
import Category from './category';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="level-one/*" element={<LevelOne />} />
        <Route path="level-two/*" element={<LevelTwo />} />
        <Route path="level-three/*" element={<LevelThree />} />
        <Route path="level-four/*" element={<LevelFour />} />
        <Route path="survey/*" element={<Survey />} />
        <Route path="pyramid-data/*" element={<PyramidData />} />
        <Route path="external-reports/*" element={<ExternalReports />} />
        <Route path="internal-reports/*" element={<InternalReports />} />
        <Route path="information-pages/*" element={<InformationPages />} />
        <Route path="field-company-objectives/*" element={<FieldCompanyObjectives />} />
        <Route path="field-kpi/*" element={<FieldKpi />} />
        <Route path="field-productype/*" element={<FieldProductype />} />
        <Route path="field-buying-criteria/*" element={<FieldBuyingCriteria />} />
        <Route path="field-attractiveness-factors/*" element={<FieldAttractivenessFactors />} />
        <Route path="field-buying-criteria-weighting/*" element={<FieldBuyingCriteriaWeighting />} />
        <Route path="field-preferred-purchase-channel/*" element={<FieldPreferredPurchaseChannel />} />
        <Route path="field-preferred-communication-channel/*" element={<FieldPreferredCommunicationChannel />} />
        <Route path="info-page-category/*" element={<InfoPageCategory />} />
        <Route path="required-sample-size/*" element={<RequiredSampleSize />} />
        <Route path="territory/*" element={<Territory />} />
        <Route path="industry/*" element={<Industry />} />
        <Route path="country/*" element={<Country />} />
        <Route path="target-market/*" element={<TargetMarket />} />
        <Route path="organisation-type/*" element={<OrganisationType />} />
        <Route path="no-of-employees/*" element={<NoOfEmployees />} />
        <Route path="revenues/*" element={<Revenues />} />
        <Route path="company-objectives/*" element={<CompanyObjectives />} />
        <Route path="strategic-focus/*" element={<StrategicFocus />} />
        <Route path="kpis/*" element={<Kpis />} />
        <Route path="marketing-budget/*" element={<MarketingBudget />} />
        <Route path="maturity-phase/*" element={<MaturityPhase />} />
        <Route path="competitive-position/*" element={<CompetitivePosition />} />
        <Route path="product-type/*" element={<ProductType />} />
        <Route path="current-market-segmentation/*" element={<CurrentMarketSegmentation />} />
        <Route path="market-segmentation-type-b-2-b/*" element={<MarketSegmentationTypeB2b />} />
        <Route path="market-segmentation-type-b-2-b-alt/*" element={<MarketSegmentationTypeB2bAlt />} />
        <Route path="market-segmentation-type-b-2-b-categories/*" element={<MarketSegmentationTypeB2bCategories />} />
        <Route path="market-segmentation-type-b-2-c/*" element={<MarketSegmentationTypeB2c />} />
        <Route path="market-segmentation-type-b-2-c-alt/*" element={<MarketSegmentationTypeB2cAlt />} />
        <Route path="market-segmentation-type-b-2-c-categories/*" element={<MarketSegmentationTypeB2cCategories />} />
        <Route path="buying-criteria/*" element={<BuyingCriteria />} />
        <Route path="buying-criteria-category/*" element={<BuyingCriteriaCategory />} />
        <Route path="market-segmentation-type/*" element={<MarketSegmentationType />} />
        <Route path="segment-unique-characteristic/*" element={<SegmentUniqueCharacteristic />} />
        <Route path="core-product-elements/*" element={<CoreProductElements />} />
        <Route path="related-service-elements/*" element={<RelatedServiceElements />} />
        <Route path="intangible-elements/*" element={<IntangibleElements />} />
        <Route path="competitor-maturity-phase/*" element={<CompetitorMaturityPhase />} />
        <Route path="competitor-competitive-position/*" element={<CompetitorCompetitivePosition />} />
        <Route path="economic-factors/*" element={<EconomicFactors />} />
        <Route path="competitive-factors/*" element={<CompetitiveFactors />} />
        <Route path="social-factors/*" element={<SocialFactors />} />
        <Route path="population-size/*" element={<PopulationSize />} />
        <Route path="statistical-error/*" element={<StatisticalError />} />
        <Route path="confidence-level/*" element={<ConfidenceLevel />} />
        <Route path="buying-criteria-weighting/*" element={<BuyingCriteriaWeighting />} />
        <Route path="age-group/*" element={<AgeGroup />} />
        <Route path="occupation/*" element={<Occupation />} />
        <Route path="preferred-communication-channel/*" element={<PreferredCommunicationChannel />} />
        <Route path="location/*" element={<Location />} />
        <Route path="education/*" element={<Education />} />
        <Route path="preferred-purchase-channel/*" element={<PreferredPurchaseChannel />} />
        <Route path="net-promoter-score/*" element={<NetPromoterScore />} />
        <Route path="market-attractiveness-factors-category/*" element={<MarketAttractivenessFactorsCategory />} />
        <Route path="segment-score-maf/*" element={<SegmentScoreMAF />} />
        <Route path="attractiveness-factors/*" element={<AttractivenessFactors />} />
        <Route path="category/*" element={<Category />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
