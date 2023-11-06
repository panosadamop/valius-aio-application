import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CompetitiveFactors from './competitive-factors';
import CompetitiveFactorsDetail from './competitive-factors-detail';
import CompetitiveFactorsUpdate from './competitive-factors-update';
import CompetitiveFactorsDeleteDialog from './competitive-factors-delete-dialog';

const CompetitiveFactorsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CompetitiveFactors />} />
    <Route path="new" element={<CompetitiveFactorsUpdate />} />
    <Route path=":id">
      <Route index element={<CompetitiveFactorsDetail />} />
      <Route path="edit" element={<CompetitiveFactorsUpdate />} />
      <Route path="delete" element={<CompetitiveFactorsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CompetitiveFactorsRoutes;
