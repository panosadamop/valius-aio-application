import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InformationPages from './information-pages';
import InformationPagesDetail from './information-pages-detail';
import InformationPagesUpdate from './information-pages-update';
import InformationPagesDeleteDialog from './information-pages-delete-dialog';

const InformationPagesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InformationPages />} />
    <Route path="new" element={<InformationPagesUpdate />} />
    <Route path=":id">
      <Route index element={<InformationPagesDetail />} />
      <Route path="edit" element={<InformationPagesUpdate />} />
      <Route path="delete" element={<InformationPagesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InformationPagesRoutes;
