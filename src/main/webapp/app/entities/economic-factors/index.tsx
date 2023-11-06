import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import EconomicFactors from './economic-factors';
import EconomicFactorsDetail from './economic-factors-detail';
import EconomicFactorsUpdate from './economic-factors-update';
import EconomicFactorsDeleteDialog from './economic-factors-delete-dialog';

const EconomicFactorsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<EconomicFactors />} />
    <Route path="new" element={<EconomicFactorsUpdate />} />
    <Route path=":id">
      <Route index element={<EconomicFactorsDetail />} />
      <Route path="edit" element={<EconomicFactorsUpdate />} />
      <Route path="delete" element={<EconomicFactorsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EconomicFactorsRoutes;
