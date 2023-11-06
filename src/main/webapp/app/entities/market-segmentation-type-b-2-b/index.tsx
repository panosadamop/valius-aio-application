import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MarketSegmentationTypeB2b from './market-segmentation-type-b-2-b';
import MarketSegmentationTypeB2bDetail from './market-segmentation-type-b-2-b-detail';
import MarketSegmentationTypeB2bUpdate from './market-segmentation-type-b-2-b-update';
import MarketSegmentationTypeB2bDeleteDialog from './market-segmentation-type-b-2-b-delete-dialog';

const MarketSegmentationTypeB2bRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MarketSegmentationTypeB2b />} />
    <Route path="new" element={<MarketSegmentationTypeB2bUpdate />} />
    <Route path=":id">
      <Route index element={<MarketSegmentationTypeB2bDetail />} />
      <Route path="edit" element={<MarketSegmentationTypeB2bUpdate />} />
      <Route path="delete" element={<MarketSegmentationTypeB2bDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MarketSegmentationTypeB2bRoutes;
