import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NoOfEmployees from './no-of-employees';
import NoOfEmployeesDetail from './no-of-employees-detail';
import NoOfEmployeesUpdate from './no-of-employees-update';
import NoOfEmployeesDeleteDialog from './no-of-employees-delete-dialog';

const NoOfEmployeesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NoOfEmployees />} />
    <Route path="new" element={<NoOfEmployeesUpdate />} />
    <Route path=":id">
      <Route index element={<NoOfEmployeesDetail />} />
      <Route path="edit" element={<NoOfEmployeesUpdate />} />
      <Route path="delete" element={<NoOfEmployeesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NoOfEmployeesRoutes;
