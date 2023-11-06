import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PopulationSize from './population-size';
import PopulationSizeDetail from './population-size-detail';
import PopulationSizeUpdate from './population-size-update';
import PopulationSizeDeleteDialog from './population-size-delete-dialog';

const PopulationSizeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PopulationSize />} />
    <Route path="new" element={<PopulationSizeUpdate />} />
    <Route path=":id">
      <Route index element={<PopulationSizeDetail />} />
      <Route path="edit" element={<PopulationSizeUpdate />} />
      <Route path="delete" element={<PopulationSizeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PopulationSizeRoutes;
