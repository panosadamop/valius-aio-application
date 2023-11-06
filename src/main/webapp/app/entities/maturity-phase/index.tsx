import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MaturityPhase from './maturity-phase';
import MaturityPhaseDetail from './maturity-phase-detail';
import MaturityPhaseUpdate from './maturity-phase-update';
import MaturityPhaseDeleteDialog from './maturity-phase-delete-dialog';

const MaturityPhaseRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MaturityPhase />} />
    <Route path="new" element={<MaturityPhaseUpdate />} />
    <Route path=":id">
      <Route index element={<MaturityPhaseDetail />} />
      <Route path="edit" element={<MaturityPhaseUpdate />} />
      <Route path="delete" element={<MaturityPhaseDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MaturityPhaseRoutes;
