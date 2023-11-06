import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MarketSegmentationTypeB2bAlt from './market-segmentation-type-b-2-b-alt';
import MarketSegmentationTypeB2bAltDetail from './market-segmentation-type-b-2-b-alt-detail';
import MarketSegmentationTypeB2bAltUpdate from './market-segmentation-type-b-2-b-alt-update';
import MarketSegmentationTypeB2bAltDeleteDialog from './market-segmentation-type-b-2-b-alt-delete-dialog';

const MarketSegmentationTypeB2bAltRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MarketSegmentationTypeB2bAlt />} />
    <Route path="new" element={<MarketSegmentationTypeB2bAltUpdate />} />
    <Route path=":id">
      <Route index element={<MarketSegmentationTypeB2bAltDetail />} />
      <Route path="edit" element={<MarketSegmentationTypeB2bAltUpdate />} />
      <Route path="delete" element={<MarketSegmentationTypeB2bAltDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MarketSegmentationTypeB2bAltRoutes;
