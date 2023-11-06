import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MarketSegmentationTypeB2bCategories from './market-segmentation-type-b-2-b-categories';
import MarketSegmentationTypeB2bCategoriesDetail from './market-segmentation-type-b-2-b-categories-detail';
import MarketSegmentationTypeB2bCategoriesUpdate from './market-segmentation-type-b-2-b-categories-update';
import MarketSegmentationTypeB2bCategoriesDeleteDialog from './market-segmentation-type-b-2-b-categories-delete-dialog';

const MarketSegmentationTypeB2bCategoriesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MarketSegmentationTypeB2bCategories />} />
    <Route path="new" element={<MarketSegmentationTypeB2bCategoriesUpdate />} />
    <Route path=":id">
      <Route index element={<MarketSegmentationTypeB2bCategoriesDetail />} />
      <Route path="edit" element={<MarketSegmentationTypeB2bCategoriesUpdate />} />
      <Route path="delete" element={<MarketSegmentationTypeB2bCategoriesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MarketSegmentationTypeB2bCategoriesRoutes;
