import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PreferredCommunicationChannel from './preferred-communication-channel';
import PreferredCommunicationChannelDetail from './preferred-communication-channel-detail';
import PreferredCommunicationChannelUpdate from './preferred-communication-channel-update';
import PreferredCommunicationChannelDeleteDialog from './preferred-communication-channel-delete-dialog';

const PreferredCommunicationChannelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PreferredCommunicationChannel />} />
    <Route path="new" element={<PreferredCommunicationChannelUpdate />} />
    <Route path=":id">
      <Route index element={<PreferredCommunicationChannelDetail />} />
      <Route path="edit" element={<PreferredCommunicationChannelUpdate />} />
      <Route path="delete" element={<PreferredCommunicationChannelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PreferredCommunicationChannelRoutes;
