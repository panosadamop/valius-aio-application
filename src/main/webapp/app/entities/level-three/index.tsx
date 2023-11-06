import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import LevelThree from './level-three';
import LevelThreeDetail from './level-three-detail';
import LevelThreeUpdate from './level-three-update';
import LevelThreeDeleteDialog from './level-three-delete-dialog';

const LevelThreeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<LevelThree />} />
    <Route path="new" element={<LevelThreeUpdate />} />
    <Route path=":id">
      <Route index element={<LevelThreeDetail />} />
      <Route path="edit" element={<LevelThreeUpdate />} />
      <Route path="delete" element={<LevelThreeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LevelThreeRoutes;
