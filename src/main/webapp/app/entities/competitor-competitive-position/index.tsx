import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CompetitorCompetitivePosition from './competitor-competitive-position';
import CompetitorCompetitivePositionDetail from './competitor-competitive-position-detail';
import CompetitorCompetitivePositionUpdate from './competitor-competitive-position-update';
import CompetitorCompetitivePositionDeleteDialog from './competitor-competitive-position-delete-dialog';

const CompetitorCompetitivePositionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CompetitorCompetitivePosition />} />
    <Route path="new" element={<CompetitorCompetitivePositionUpdate />} />
    <Route path=":id">
      <Route index element={<CompetitorCompetitivePositionDetail />} />
      <Route path="edit" element={<CompetitorCompetitivePositionUpdate />} />
      <Route path="delete" element={<CompetitorCompetitivePositionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompetitorCompetitivePositionRoutes;
