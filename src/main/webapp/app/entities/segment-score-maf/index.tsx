import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SegmentScoreMAF from './segment-score-maf';
import SegmentScoreMAFDetail from './segment-score-maf-detail';
import SegmentScoreMAFUpdate from './segment-score-maf-update';
import SegmentScoreMAFDeleteDialog from './segment-score-maf-delete-dialog';

const SegmentScoreMAFRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SegmentScoreMAF />} />
    <Route path="new" element={<SegmentScoreMAFUpdate />} />
    <Route path=":id">
      <Route index element={<SegmentScoreMAFDetail />} />
      <Route path="edit" element={<SegmentScoreMAFUpdate />} />
      <Route path="delete" element={<SegmentScoreMAFDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SegmentScoreMAFRoutes;
