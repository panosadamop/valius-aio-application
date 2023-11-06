import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TargetMarket from './target-market';
import TargetMarketDetail from './target-market-detail';
import TargetMarketUpdate from './target-market-update';
import TargetMarketDeleteDialog from './target-market-delete-dialog';

const TargetMarketRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TargetMarket />} />
    <Route path="new" element={<TargetMarketUpdate />} />
    <Route path=":id">
      <Route index element={<TargetMarketDetail />} />
      <Route path="edit" element={<TargetMarketUpdate />} />
      <Route path="delete" element={<TargetMarketDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TargetMarketRoutes;
