import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldBuyingCriteriaWeighting from './field-buying-criteria-weighting';
import FieldBuyingCriteriaWeightingDetail from './field-buying-criteria-weighting-detail';
import FieldBuyingCriteriaWeightingUpdate from './field-buying-criteria-weighting-update';
import FieldBuyingCriteriaWeightingDeleteDialog from './field-buying-criteria-weighting-delete-dialog';

const FieldBuyingCriteriaWeightingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldBuyingCriteriaWeighting />} />
    <Route path="new" element={<FieldBuyingCriteriaWeightingUpdate />} />
    <Route path=":id">
      <Route index element={<FieldBuyingCriteriaWeightingDetail />} />
      <Route path="edit" element={<FieldBuyingCriteriaWeightingUpdate />} />
      <Route path="delete" element={<FieldBuyingCriteriaWeightingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldBuyingCriteriaWeightingRoutes;
