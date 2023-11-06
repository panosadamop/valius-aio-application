import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Occupation from './occupation';
import OccupationDetail from './occupation-detail';
import OccupationUpdate from './occupation-update';
import OccupationDeleteDialog from './occupation-delete-dialog';

const OccupationRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Occupation />} />
    <Route path="new" element={<OccupationUpdate />} />
    <Route path=":id">
      <Route index element={<OccupationDetail />} />
      <Route path="edit" element={<OccupationUpdate />} />
      <Route path="delete" element={<OccupationDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OccupationRoutes;
