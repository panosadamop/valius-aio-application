import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StrategicFocus from './strategic-focus';
import StrategicFocusDetail from './strategic-focus-detail';
import StrategicFocusUpdate from './strategic-focus-update';
import StrategicFocusDeleteDialog from './strategic-focus-delete-dialog';

const StrategicFocusRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StrategicFocus />} />
    <Route path="new" element={<StrategicFocusUpdate />} />
    <Route path=":id">
      <Route index element={<StrategicFocusDetail />} />
      <Route path="edit" element={<StrategicFocusUpdate />} />
      <Route path="delete" element={<StrategicFocusDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StrategicFocusRoutes;
