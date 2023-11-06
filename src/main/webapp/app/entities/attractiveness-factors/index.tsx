import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import AttractivenessFactors from './attractiveness-factors';
import AttractivenessFactorsDetail from './attractiveness-factors-detail';
import AttractivenessFactorsUpdate from './attractiveness-factors-update';
import AttractivenessFactorsDeleteDialog from './attractiveness-factors-delete-dialog';

const AttractivenessFactorsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<AttractivenessFactors />} />
    <Route path="new" element={<AttractivenessFactorsUpdate />} />
    <Route path=":id">
      <Route index element={<AttractivenessFactorsDetail />} />
      <Route path="edit" element={<AttractivenessFactorsUpdate />} />
      <Route path="delete" element={<AttractivenessFactorsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AttractivenessFactorsRoutes;
