import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import IntangibleElements from './intangible-elements';
import IntangibleElementsDetail from './intangible-elements-detail';
import IntangibleElementsUpdate from './intangible-elements-update';
import IntangibleElementsDeleteDialog from './intangible-elements-delete-dialog';

const IntangibleElementsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<IntangibleElements />} />
    <Route path="new" element={<IntangibleElementsUpdate />} />
    <Route path=":id">
      <Route index element={<IntangibleElementsDetail />} />
      <Route path="edit" element={<IntangibleElementsUpdate />} />
      <Route path="delete" element={<IntangibleElementsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IntangibleElementsRoutes;
