import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Industry from './industry';
import IndustryDetail from './industry-detail';
import IndustryUpdate from './industry-update';
import IndustryDeleteDialog from './industry-delete-dialog';

const IndustryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Industry />} />
    <Route path="new" element={<IndustryUpdate />} />
    <Route path=":id">
      <Route index element={<IndustryDetail />} />
      <Route path="edit" element={<IndustryUpdate />} />
      <Route path="delete" element={<IndustryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IndustryRoutes;
