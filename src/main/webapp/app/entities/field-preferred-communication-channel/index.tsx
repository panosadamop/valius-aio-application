import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldPreferredCommunicationChannel from './field-preferred-communication-channel';
import FieldPreferredCommunicationChannelDetail from './field-preferred-communication-channel-detail';
import FieldPreferredCommunicationChannelUpdate from './field-preferred-communication-channel-update';
import FieldPreferredCommunicationChannelDeleteDialog from './field-preferred-communication-channel-delete-dialog';

const FieldPreferredCommunicationChannelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldPreferredCommunicationChannel />} />
    <Route path="new" element={<FieldPreferredCommunicationChannelUpdate />} />
    <Route path=":id">
      <Route index element={<FieldPreferredCommunicationChannelDetail />} />
      <Route path="edit" element={<FieldPreferredCommunicationChannelUpdate />} />
      <Route path="delete" element={<FieldPreferredCommunicationChannelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldPreferredCommunicationChannelRoutes;
