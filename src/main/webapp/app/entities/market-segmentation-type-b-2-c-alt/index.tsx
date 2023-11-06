import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MarketSegmentationTypeB2cAlt from './market-segmentation-type-b-2-c-alt';
import MarketSegmentationTypeB2cAltDetail from './market-segmentation-type-b-2-c-alt-detail';
import MarketSegmentationTypeB2cAltUpdate from './market-segmentation-type-b-2-c-alt-update';
import MarketSegmentationTypeB2cAltDeleteDialog from './market-segmentation-type-b-2-c-alt-delete-dialog';

const MarketSegmentationTypeB2cAltRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MarketSegmentationTypeB2cAlt />} />
    <Route path="new" element={<MarketSegmentationTypeB2cAltUpdate />} />
    <Route path=":id">
      <Route index element={<MarketSegmentationTypeB2cAltDetail />} />
      <Route path="edit" element={<MarketSegmentationTypeB2cAltUpdate />} />
      <Route path="delete" element={<MarketSegmentationTypeB2cAltDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MarketSegmentationTypeB2cAltRoutes;
