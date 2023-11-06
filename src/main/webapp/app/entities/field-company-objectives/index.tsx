import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldCompanyObjectives from './field-company-objectives';
import FieldCompanyObjectivesDetail from './field-company-objectives-detail';
import FieldCompanyObjectivesUpdate from './field-company-objectives-update';
import FieldCompanyObjectivesDeleteDialog from './field-company-objectives-delete-dialog';

const FieldCompanyObjectivesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldCompanyObjectives />} />
    <Route path="new" element={<FieldCompanyObjectivesUpdate />} />
    <Route path=":id">
      <Route index element={<FieldCompanyObjectivesDetail />} />
      <Route path="edit" element={<FieldCompanyObjectivesUpdate />} />
      <Route path="delete" element={<FieldCompanyObjectivesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldCompanyObjectivesRoutes;
