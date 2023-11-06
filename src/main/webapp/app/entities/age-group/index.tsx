import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AgeGroup from './age-group';
import AgeGroupDetail from './age-group-detail';
import AgeGroupUpdate from './age-group-update';
import AgeGroupDeleteDialog from './age-group-delete-dialog';

const AgeGroupRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AgeGroup />} />
    <Route path="new" element={<AgeGroupUpdate />} />
    <Route path=":id">
      <Route index element={<AgeGroupDetail />} />
      <Route path="edit" element={<AgeGroupUpdate />} />
      <Route path="delete" element={<AgeGroupDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AgeGroupRoutes;
