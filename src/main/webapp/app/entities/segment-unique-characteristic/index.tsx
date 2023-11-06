import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SegmentUniqueCharacteristic from './segment-unique-characteristic';
import SegmentUniqueCharacteristicDetail from './segment-unique-characteristic-detail';
import SegmentUniqueCharacteristicUpdate from './segment-unique-characteristic-update';
import SegmentUniqueCharacteristicDeleteDialog from './segment-unique-characteristic-delete-dialog';

const SegmentUniqueCharacteristicRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SegmentUniqueCharacteristic />} />
    <Route path="new" element={<SegmentUniqueCharacteristicUpdate />} />
    <Route path=":id">
      <Route index element={<SegmentUniqueCharacteristicDetail />} />
      <Route path="edit" element={<SegmentUniqueCharacteristicUpdate />} />
      <Route path="delete" element={<SegmentUniqueCharacteristicDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SegmentUniqueCharacteristicRoutes;
