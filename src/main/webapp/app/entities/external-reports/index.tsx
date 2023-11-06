import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ExternalReports from './external-reports';
import ExternalReportsDetail from './external-reports-detail';
import ExternalReportsUpdate from './external-reports-update';
import ExternalReportsDeleteDialog from './external-reports-delete-dialog';

const ExternalReportsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ExternalReports />} />
    <Route path="new" element={<ExternalReportsUpdate />} />
    <Route path=":id">
      <Route index element={<ExternalReportsDetail />} />
      <Route path="edit" element={<ExternalReportsUpdate />} />
      <Route path="delete" element={<ExternalReportsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ExternalReportsRoutes;
