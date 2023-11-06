import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MarketSegmentationType from './market-segmentation-type';
import MarketSegmentationTypeDetail from './market-segmentation-type-detail';
import MarketSegmentationTypeUpdate from './market-segmentation-type-update';
import MarketSegmentationTypeDeleteDialog from './market-segmentation-type-delete-dialog';

const MarketSegmentationTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MarketSegmentationType />} />
    <Route path="new" element={<MarketSegmentationTypeUpdate />} />
    <Route path=":id">
      <Route index element={<MarketSegmentationTypeDetail />} />
      <Route path="edit" element={<MarketSegmentationTypeUpdate />} />
      <Route path="delete" element={<MarketSegmentationTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MarketSegmentationTypeRoutes;
