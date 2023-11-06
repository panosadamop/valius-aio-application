import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldBuyingCriteria from './field-buying-criteria';
import FieldBuyingCriteriaDetail from './field-buying-criteria-detail';
import FieldBuyingCriteriaUpdate from './field-buying-criteria-update';
import FieldBuyingCriteriaDeleteDialog from './field-buying-criteria-delete-dialog';

const FieldBuyingCriteriaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldBuyingCriteria />} />
    <Route path="new" element={<FieldBuyingCriteriaUpdate />} />
    <Route path=":id">
      <Route index element={<FieldBuyingCriteriaDetail />} />
      <Route path="edit" element={<FieldBuyingCriteriaUpdate />} />
      <Route path="delete" element={<FieldBuyingCriteriaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldBuyingCriteriaRoutes;
