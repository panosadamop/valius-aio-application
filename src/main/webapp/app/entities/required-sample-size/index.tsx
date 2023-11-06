import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RequiredSampleSize from './required-sample-size';
import RequiredSampleSizeDetail from './required-sample-size-detail';
import RequiredSampleSizeUpdate from './required-sample-size-update';
import RequiredSampleSizeDeleteDialog from './required-sample-size-delete-dialog';

const RequiredSampleSizeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RequiredSampleSize />} />
    <Route path="new" element={<RequiredSampleSizeUpdate />} />
    <Route path=":id">
      <Route index element={<RequiredSampleSizeDetail />} />
      <Route path="edit" element={<RequiredSampleSizeUpdate />} />
      <Route path="delete" element={<RequiredSampleSizeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RequiredSampleSizeRoutes;
