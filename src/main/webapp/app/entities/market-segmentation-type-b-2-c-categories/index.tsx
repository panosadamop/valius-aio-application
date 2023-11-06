import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MarketSegmentationTypeB2cCategories from './market-segmentation-type-b-2-c-categories';
import MarketSegmentationTypeB2cCategoriesDetail from './market-segmentation-type-b-2-c-categories-detail';
import MarketSegmentationTypeB2cCategoriesUpdate from './market-segmentation-type-b-2-c-categories-update';
import MarketSegmentationTypeB2cCategoriesDeleteDialog from './market-segmentation-type-b-2-c-categories-delete-dialog';

const MarketSegmentationTypeB2cCategoriesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MarketSegmentationTypeB2cCategories />} />
    <Route path="new" element={<MarketSegmentationTypeB2cCategoriesUpdate />} />
    <Route path=":id">
      <Route index element={<MarketSegmentationTypeB2cCategoriesDetail />} />
      <Route path="edit" element={<MarketSegmentationTypeB2cCategoriesUpdate />} />
      <Route path="delete" element={<MarketSegmentationTypeB2cCategoriesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MarketSegmentationTypeB2cCategoriesRoutes;
