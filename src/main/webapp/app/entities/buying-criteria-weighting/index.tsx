import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BuyingCriteriaWeighting from './buying-criteria-weighting';
import BuyingCriteriaWeightingDetail from './buying-criteria-weighting-detail';
import BuyingCriteriaWeightingUpdate from './buying-criteria-weighting-update';
import BuyingCriteriaWeightingDeleteDialog from './buying-criteria-weighting-delete-dialog';

const BuyingCriteriaWeightingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BuyingCriteriaWeighting />} />
    <Route path="new" element={<BuyingCriteriaWeightingUpdate />} />
    <Route path=":id">
      <Route index element={<BuyingCriteriaWeightingDetail />} />
      <Route path="edit" element={<BuyingCriteriaWeightingUpdate />} />
      <Route path="delete" element={<BuyingCriteriaWeightingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BuyingCriteriaWeightingRoutes;
