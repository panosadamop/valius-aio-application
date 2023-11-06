import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CompetitorMaturityPhase from './competitor-maturity-phase';
import CompetitorMaturityPhaseDetail from './competitor-maturity-phase-detail';
import CompetitorMaturityPhaseUpdate from './competitor-maturity-phase-update';
import CompetitorMaturityPhaseDeleteDialog from './competitor-maturity-phase-delete-dialog';

const CompetitorMaturityPhaseRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CompetitorMaturityPhase />} />
    <Route path="new" element={<CompetitorMaturityPhaseUpdate />} />
    <Route path=":id">
      <Route index element={<CompetitorMaturityPhaseDetail />} />
      <Route path="edit" element={<CompetitorMaturityPhaseUpdate />} />
      <Route path="delete" element={<CompetitorMaturityPhaseDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompetitorMaturityPhaseRoutes;
