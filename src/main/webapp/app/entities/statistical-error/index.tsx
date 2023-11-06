import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import StatisticalError from './statistical-error';
import StatisticalErrorDetail from './statistical-error-detail';
import StatisticalErrorUpdate from './statistical-error-update';
import StatisticalErrorDeleteDialog from './statistical-error-delete-dialog';

const StatisticalErrorRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<StatisticalError />} />
    <Route path="new" element={<StatisticalErrorUpdate />} />
    <Route path=":id">
      <Route index element={<StatisticalErrorDetail />} />
      <Route path="edit" element={<StatisticalErrorUpdate />} />
      <Route path="delete" element={<StatisticalErrorDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StatisticalErrorRoutes;
