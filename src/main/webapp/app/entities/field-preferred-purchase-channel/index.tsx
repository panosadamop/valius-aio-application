import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldPreferredPurchaseChannel from './field-preferred-purchase-channel';
import FieldPreferredPurchaseChannelDetail from './field-preferred-purchase-channel-detail';
import FieldPreferredPurchaseChannelUpdate from './field-preferred-purchase-channel-update';
import FieldPreferredPurchaseChannelDeleteDialog from './field-preferred-purchase-channel-delete-dialog';

const FieldPreferredPurchaseChannelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldPreferredPurchaseChannel />} />
    <Route path="new" element={<FieldPreferredPurchaseChannelUpdate />} />
    <Route path=":id">
      <Route index element={<FieldPreferredPurchaseChannelDetail />} />
      <Route path="edit" element={<FieldPreferredPurchaseChannelUpdate />} />
      <Route path="delete" element={<FieldPreferredPurchaseChannelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldPreferredPurchaseChannelRoutes;
