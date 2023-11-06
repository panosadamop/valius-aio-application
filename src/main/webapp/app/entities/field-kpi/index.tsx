import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldKpi from './field-kpi';
import FieldKpiDetail from './field-kpi-detail';
import FieldKpiUpdate from './field-kpi-update';
import FieldKpiDeleteDialog from './field-kpi-delete-dialog';

const FieldKpiRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldKpi />} />
    <Route path="new" element={<FieldKpiUpdate />} />
    <Route path=":id">
      <Route index element={<FieldKpiDetail />} />
      <Route path="edit" element={<FieldKpiUpdate />} />
      <Route path="delete" element={<FieldKpiDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldKpiRoutes;
