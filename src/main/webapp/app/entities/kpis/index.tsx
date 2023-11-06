import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kpis from './kpis';
import KpisDetail from './kpis-detail';
import KpisUpdate from './kpis-update';
import KpisDeleteDialog from './kpis-delete-dialog';

const KpisRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kpis />} />
    <Route path="new" element={<KpisUpdate />} />
    <Route path=":id">
      <Route index element={<KpisDetail />} />
      <Route path="edit" element={<KpisUpdate />} />
      <Route path="delete" element={<KpisDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KpisRoutes;
