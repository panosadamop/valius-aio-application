import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Revenues from './revenues';
import RevenuesDetail from './revenues-detail';
import RevenuesUpdate from './revenues-update';
import RevenuesDeleteDialog from './revenues-delete-dialog';

const RevenuesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Revenues />} />
    <Route path="new" element={<RevenuesUpdate />} />
    <Route path=":id">
      <Route index element={<RevenuesDetail />} />
      <Route path="edit" element={<RevenuesUpdate />} />
      <Route path="delete" element={<RevenuesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RevenuesRoutes;
