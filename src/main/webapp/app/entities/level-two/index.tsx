import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LevelTwo from './level-two';
import LevelTwoDetail from './level-two-detail';
import LevelTwoUpdate from './level-two-update';
import LevelTwoDeleteDialog from './level-two-delete-dialog';

const LevelTwoRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LevelTwo />} />
    <Route path="new" element={<LevelTwoUpdate />} />
    <Route path=":id">
      <Route index element={<LevelTwoDetail />} />
      <Route path="edit" element={<LevelTwoUpdate />} />
      <Route path="delete" element={<LevelTwoDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LevelTwoRoutes;
