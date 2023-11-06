import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LevelOne from './level-one';
import LevelOneDetail from './level-one-detail';
import LevelOneUpdate from './level-one-update';
import LevelOneDeleteDialog from './level-one-delete-dialog';

const LevelOneRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LevelOne />} />
    <Route path="new" element={<LevelOneUpdate />} />
    <Route path=":id">
      <Route index element={<LevelOneDetail />} />
      <Route path="edit" element={<LevelOneUpdate />} />
      <Route path="delete" element={<LevelOneDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LevelOneRoutes;
