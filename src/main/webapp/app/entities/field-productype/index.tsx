import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldProductype from './field-productype';
import FieldProductypeDetail from './field-productype-detail';
import FieldProductypeUpdate from './field-productype-update';
import FieldProductypeDeleteDialog from './field-productype-delete-dialog';

const FieldProductypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldProductype />} />
    <Route path="new" element={<FieldProductypeUpdate />} />
    <Route path=":id">
      <Route index element={<FieldProductypeDetail />} />
      <Route path="edit" element={<FieldProductypeUpdate />} />
      <Route path="delete" element={<FieldProductypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldProductypeRoutes;
