import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ConfidenceLevel from './confidence-level';
import ConfidenceLevelDetail from './confidence-level-detail';
import ConfidenceLevelUpdate from './confidence-level-update';
import ConfidenceLevelDeleteDialog from './confidence-level-delete-dialog';

const ConfidenceLevelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ConfidenceLevel />} />
    <Route path="new" element={<ConfidenceLevelUpdate />} />
    <Route path=":id">
      <Route index element={<ConfidenceLevelDetail />} />
      <Route path="edit" element={<ConfidenceLevelUpdate />} />
      <Route path="delete" element={<ConfidenceLevelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ConfidenceLevelRoutes;
