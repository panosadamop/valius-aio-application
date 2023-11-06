import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InternalReports from './internal-reports';
import InternalReportsDetail from './internal-reports-detail';
import InternalReportsUpdate from './internal-reports-update';
import InternalReportsDeleteDialog from './internal-reports-delete-dialog';

const InternalReportsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InternalReports />} />
    <Route path="new" element={<InternalReportsUpdate />} />
    <Route path=":id">
      <Route index element={<InternalReportsDetail />} />
      <Route path="edit" element={<InternalReportsUpdate />} />
      <Route path="delete" element={<InternalReportsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InternalReportsRoutes;
