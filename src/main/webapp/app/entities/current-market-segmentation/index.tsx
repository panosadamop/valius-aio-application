import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CurrentMarketSegmentation from './current-market-segmentation';
import CurrentMarketSegmentationDetail from './current-market-segmentation-detail';
import CurrentMarketSegmentationUpdate from './current-market-segmentation-update';
import CurrentMarketSegmentationDeleteDialog from './current-market-segmentation-delete-dialog';

const CurrentMarketSegmentationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CurrentMarketSegmentation />} />
    <Route path="new" element={<CurrentMarketSegmentationUpdate />} />
    <Route path=":id">
      <Route index element={<CurrentMarketSegmentationDetail />} />
      <Route path="edit" element={<CurrentMarketSegmentationUpdate />} />
      <Route path="delete" element={<CurrentMarketSegmentationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CurrentMarketSegmentationRoutes;
