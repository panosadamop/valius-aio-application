import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PyramidData from './pyramid-data';
import PyramidDataDetail from './pyramid-data-detail';
import PyramidDataUpdate from './pyramid-data-update';
import PyramidDataDeleteDialog from './pyramid-data-delete-dialog';

const PyramidDataRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PyramidData />} />
    <Route path="new" element={<PyramidDataUpdate />} />
    <Route path=":id">
      <Route index element={<PyramidDataDetail />} />
      <Route path="edit" element={<PyramidDataUpdate />} />
      <Route path="delete" element={<PyramidDataDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PyramidDataRoutes;
