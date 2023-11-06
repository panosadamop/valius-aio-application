import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PreferredPurchaseChannel from './preferred-purchase-channel';
import PreferredPurchaseChannelDetail from './preferred-purchase-channel-detail';
import PreferredPurchaseChannelUpdate from './preferred-purchase-channel-update';
import PreferredPurchaseChannelDeleteDialog from './preferred-purchase-channel-delete-dialog';

const PreferredPurchaseChannelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PreferredPurchaseChannel />} />
    <Route path="new" element={<PreferredPurchaseChannelUpdate />} />
    <Route path=":id">
      <Route index element={<PreferredPurchaseChannelDetail />} />
      <Route path="edit" element={<PreferredPurchaseChannelUpdate />} />
      <Route path="delete" element={<PreferredPurchaseChannelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PreferredPurchaseChannelRoutes;
