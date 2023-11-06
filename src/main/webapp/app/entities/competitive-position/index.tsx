import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CompetitivePosition from './competitive-position';
import CompetitivePositionDetail from './competitive-position-detail';
import CompetitivePositionUpdate from './competitive-position-update';
import CompetitivePositionDeleteDialog from './competitive-position-delete-dialog';

const CompetitivePositionRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CompetitivePosition />} />
    <Route path="new" element={<CompetitivePositionUpdate />} />
    <Route path=":id">
      <Route index element={<CompetitivePositionDetail />} />
      <Route path="edit" element={<CompetitivePositionUpdate />} />
      <Route path="delete" element={<CompetitivePositionDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompetitivePositionRoutes;
