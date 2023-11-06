import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MarketAttractivenessFactorsCategory from './market-attractiveness-factors-category';
import MarketAttractivenessFactorsCategoryDetail from './market-attractiveness-factors-category-detail';
import MarketAttractivenessFactorsCategoryUpdate from './market-attractiveness-factors-category-update';
import MarketAttractivenessFactorsCategoryDeleteDialog from './market-attractiveness-factors-category-delete-dialog';

const MarketAttractivenessFactorsCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MarketAttractivenessFactorsCategory />} />
    <Route path="new" element={<MarketAttractivenessFactorsCategoryUpdate />} />
    <Route path=":id">
      <Route index element={<MarketAttractivenessFactorsCategoryDetail />} />
      <Route path="edit" element={<MarketAttractivenessFactorsCategoryUpdate />} />
      <Route path="delete" element={<MarketAttractivenessFactorsCategoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MarketAttractivenessFactorsCategoryRoutes;
