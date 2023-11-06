import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CompanyObjectives from './company-objectives';
import CompanyObjectivesDetail from './company-objectives-detail';
import CompanyObjectivesUpdate from './company-objectives-update';
import CompanyObjectivesDeleteDialog from './company-objectives-delete-dialog';

const CompanyObjectivesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CompanyObjectives />} />
    <Route path="new" element={<CompanyObjectivesUpdate />} />
    <Route path=":id">
      <Route index element={<CompanyObjectivesDetail />} />
      <Route path="edit" element={<CompanyObjectivesUpdate />} />
      <Route path="delete" element={<CompanyObjectivesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompanyObjectivesRoutes;
