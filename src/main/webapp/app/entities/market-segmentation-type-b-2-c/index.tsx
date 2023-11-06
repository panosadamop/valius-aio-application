import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MarketSegmentationTypeB2c from './market-segmentation-type-b-2-c';
import MarketSegmentationTypeB2cDetail from './market-segmentation-type-b-2-c-detail';
import MarketSegmentationTypeB2cUpdate from './market-segmentation-type-b-2-c-update';
import MarketSegmentationTypeB2cDeleteDialog from './market-segmentation-type-b-2-c-delete-dialog';

const MarketSegmentationTypeB2cRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MarketSegmentationTypeB2c />} />
    <Route path="new" element={<MarketSegmentationTypeB2cUpdate />} />
    <Route path=":id">
      <Route index element={<MarketSegmentationTypeB2cDetail />} />
      <Route path="edit" element={<MarketSegmentationTypeB2cUpdate />} />
      <Route path="delete" element={<MarketSegmentationTypeB2cDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MarketSegmentationTypeB2cRoutes;
