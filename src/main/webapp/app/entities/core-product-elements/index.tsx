import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import CoreProductElements from './core-product-elements';
import CoreProductElementsDetail from './core-product-elements-detail';
import CoreProductElementsUpdate from './core-product-elements-update';
import CoreProductElementsDeleteDialog from './core-product-elements-delete-dialog';

const CoreProductElementsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<CoreProductElements />} />
    <Route path="new" element={<CoreProductElementsUpdate />} />
    <Route path=":id">
      <Route index element={<CoreProductElementsDetail />} />
      <Route path="edit" element={<CoreProductElementsUpdate />} />
      <Route path="delete" element={<CoreProductElementsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CoreProductElementsRoutes;
