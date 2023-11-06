import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NetPromoterScore from './net-promoter-score';
import NetPromoterScoreDetail from './net-promoter-score-detail';
import NetPromoterScoreUpdate from './net-promoter-score-update';
import NetPromoterScoreDeleteDialog from './net-promoter-score-delete-dialog';

const NetPromoterScoreRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NetPromoterScore />} />
    <Route path="new" element={<NetPromoterScoreUpdate />} />
    <Route path=":id">
      <Route index element={<NetPromoterScoreDetail />} />
      <Route path="edit" element={<NetPromoterScoreUpdate />} />
      <Route path="delete" element={<NetPromoterScoreDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NetPromoterScoreRoutes;
