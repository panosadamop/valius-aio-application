import levelOne from 'app/entities/level-one/level-one.reducer';
import levelTwo from 'app/entities/level-two/level-two.reducer';
import levelThree from 'app/entities/level-three/level-three.reducer';
import levelFour from 'app/entities/level-four/level-four.reducer';
import survey from 'app/entities/survey/survey.reducer';
import pyramidData from 'app/entities/pyramid-data/pyramid-data.reducer';
import externalReports from 'app/entities/external-reports/external-reports.reducer';
import internalReports from 'app/entities/internal-reports/internal-reports.reducer';
import informationPages from 'app/entities/information-pages/information-pages.reducer';
import fieldCompanyObjectives from 'app/entities/field-company-objectives/field-company-objectives.reducer';
import fieldKpi from 'app/entities/field-kpi/field-kpi.reducer';
import fieldProductype from 'app/entities/field-productype/field-productype.reducer';
import fieldBuyingCriteria from 'app/entities/field-buying-criteria/field-buying-criteria.reducer';
import fieldAttractivenessFactors from 'app/entities/field-attractiveness-factors/field-attractiveness-factors.reducer';
import fieldBuyingCriteriaWeighting from 'app/entities/field-buying-criteria-weighting/field-buying-criteria-weighting.reducer';
import fieldPreferredPurchaseChannel from 'app/entities/field-preferred-purchase-channel/field-preferred-purchase-channel.reducer';
import fieldPreferredCommunicationChannel from 'app/entities/field-preferred-communication-channel/field-preferred-communication-channel.reducer';
import infoPageCategory from 'app/entities/info-page-category/info-page-category.reducer';
import requiredSampleSize from 'app/entities/required-sample-size/required-sample-size.reducer';
import territory from 'app/entities/territory/territory.reducer';
import industry from 'app/entities/industry/industry.reducer';
import country from 'app/entities/country/country.reducer';
import targetMarket from 'app/entities/target-market/target-market.reducer';
import organisationType from 'app/entities/organisation-type/organisation-type.reducer';
import noOfEmployees from 'app/entities/no-of-employees/no-of-employees.reducer';
import revenues from 'app/entities/revenues/revenues.reducer';
import companyObjectives from 'app/entities/company-objectives/company-objectives.reducer';
import strategicFocus from 'app/entities/strategic-focus/strategic-focus.reducer';
import kpis from 'app/entities/kpis/kpis.reducer';
import marketingBudget from 'app/entities/marketing-budget/marketing-budget.reducer';
import maturityPhase from 'app/entities/maturity-phase/maturity-phase.reducer';
import competitivePosition from 'app/entities/competitive-position/competitive-position.reducer';
import productType from 'app/entities/product-type/product-type.reducer';
import currentMarketSegmentation from 'app/entities/current-market-segmentation/current-market-segmentation.reducer';
import marketSegmentationTypeB2b from 'app/entities/market-segmentation-type-b-2-b/market-segmentation-type-b-2-b.reducer';
import marketSegmentationTypeB2bAlt from 'app/entities/market-segmentation-type-b-2-b-alt/market-segmentation-type-b-2-b-alt.reducer';
import marketSegmentationTypeB2bCategories from 'app/entities/market-segmentation-type-b-2-b-categories/market-segmentation-type-b-2-b-categories.reducer';
import marketSegmentationTypeB2c from 'app/entities/market-segmentation-type-b-2-c/market-segmentation-type-b-2-c.reducer';
import marketSegmentationTypeB2cAlt from 'app/entities/market-segmentation-type-b-2-c-alt/market-segmentation-type-b-2-c-alt.reducer';
import marketSegmentationTypeB2cCategories from 'app/entities/market-segmentation-type-b-2-c-categories/market-segmentation-type-b-2-c-categories.reducer';
import buyingCriteria from 'app/entities/buying-criteria/buying-criteria.reducer';
import buyingCriteriaCategory from 'app/entities/buying-criteria-category/buying-criteria-category.reducer';
import marketSegmentationType from 'app/entities/market-segmentation-type/market-segmentation-type.reducer';
import segmentUniqueCharacteristic from 'app/entities/segment-unique-characteristic/segment-unique-characteristic.reducer';
import coreProductElements from 'app/entities/core-product-elements/core-product-elements.reducer';
import relatedServiceElements from 'app/entities/related-service-elements/related-service-elements.reducer';
import intangibleElements from 'app/entities/intangible-elements/intangible-elements.reducer';
import competitorMaturityPhase from 'app/entities/competitor-maturity-phase/competitor-maturity-phase.reducer';
import competitorCompetitivePosition from 'app/entities/competitor-competitive-position/competitor-competitive-position.reducer';
import economicFactors from 'app/entities/economic-factors/economic-factors.reducer';
import competitiveFactors from 'app/entities/competitive-factors/competitive-factors.reducer';
import socialFactors from 'app/entities/social-factors/social-factors.reducer';
import populationSize from 'app/entities/population-size/population-size.reducer';
import statisticalError from 'app/entities/statistical-error/statistical-error.reducer';
import confidenceLevel from 'app/entities/confidence-level/confidence-level.reducer';
import buyingCriteriaWeighting from 'app/entities/buying-criteria-weighting/buying-criteria-weighting.reducer';
import ageGroup from 'app/entities/age-group/age-group.reducer';
import occupation from 'app/entities/occupation/occupation.reducer';
import preferredCommunicationChannel from 'app/entities/preferred-communication-channel/preferred-communication-channel.reducer';
import location from 'app/entities/location/location.reducer';
import education from 'app/entities/education/education.reducer';
import preferredPurchaseChannel from 'app/entities/preferred-purchase-channel/preferred-purchase-channel.reducer';
import netPromoterScore from 'app/entities/net-promoter-score/net-promoter-score.reducer';
import marketAttractivenessFactorsCategory from 'app/entities/market-attractiveness-factors-category/market-attractiveness-factors-category.reducer';
import segmentScoreMAF from 'app/entities/segment-score-maf/segment-score-maf.reducer';
import attractivenessFactors from 'app/entities/attractiveness-factors/attractiveness-factors.reducer';
import category from 'app/entities/category/category.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

const entitiesReducers = {
  levelOne,
  levelTwo,
  levelThree,
  levelFour,
  survey,
  pyramidData,
  externalReports,
  internalReports,
  informationPages,
  fieldCompanyObjectives,
  fieldKpi,
  fieldProductype,
  fieldBuyingCriteria,
  fieldAttractivenessFactors,
  fieldBuyingCriteriaWeighting,
  fieldPreferredPurchaseChannel,
  fieldPreferredCommunicationChannel,
  infoPageCategory,
  requiredSampleSize,
  territory,
  industry,
  country,
  targetMarket,
  organisationType,
  noOfEmployees,
  revenues,
  companyObjectives,
  strategicFocus,
  kpis,
  marketingBudget,
  maturityPhase,
  competitivePosition,
  productType,
  currentMarketSegmentation,
  marketSegmentationTypeB2b,
  marketSegmentationTypeB2bAlt,
  marketSegmentationTypeB2bCategories,
  marketSegmentationTypeB2c,
  marketSegmentationTypeB2cAlt,
  marketSegmentationTypeB2cCategories,
  buyingCriteria,
  buyingCriteriaCategory,
  marketSegmentationType,
  segmentUniqueCharacteristic,
  coreProductElements,
  relatedServiceElements,
  intangibleElements,
  competitorMaturityPhase,
  competitorCompetitivePosition,
  economicFactors,
  competitiveFactors,
  socialFactors,
  populationSize,
  statisticalError,
  confidenceLevel,
  buyingCriteriaWeighting,
  ageGroup,
  occupation,
  preferredCommunicationChannel,
  location,
  education,
  preferredPurchaseChannel,
  netPromoterScore,
  marketAttractivenessFactorsCategory,
  segmentScoreMAF,
  attractivenessFactors,
  category,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
};

export default entitiesReducers;
