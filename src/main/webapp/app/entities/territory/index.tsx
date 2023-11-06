import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Territory from './territory';
import TerritoryDetail from './territory-detail';
import TerritoryUpdate from './territory-update';
import TerritoryDeleteDialog from './territory-delete-dialog';

const TerritoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Territory />} />
    <Route path="new" element={<TerritoryUpdate />} />
    <Route path=":id">
      <Route index element={<TerritoryDetail />} />
      <Route path="edit" element={<TerritoryUpdate />} />
      <Route path="delete" element={<TerritoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TerritoryRoutes;
