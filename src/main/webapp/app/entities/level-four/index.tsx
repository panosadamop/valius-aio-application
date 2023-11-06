import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LevelFour from './level-four';
import LevelFourDetail from './level-four-detail';
import LevelFourUpdate from './level-four-update';
import LevelFourDeleteDialog from './level-four-delete-dialog';

const LevelFourRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LevelFour />} />
    <Route path="new" element={<LevelFourUpdate />} />
    <Route path=":id">
      <Route index element={<LevelFourDetail />} />
      <Route path="edit" element={<LevelFourUpdate />} />
      <Route path="delete" element={<LevelFourDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LevelFourRoutes;
