import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BuyingCriteria from './buying-criteria';
import BuyingCriteriaDetail from './buying-criteria-detail';
import BuyingCriteriaUpdate from './buying-criteria-update';
import BuyingCriteriaDeleteDialog from './buying-criteria-delete-dialog';

const BuyingCriteriaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BuyingCriteria />} />
    <Route path="new" element={<BuyingCriteriaUpdate />} />
    <Route path=":id">
      <Route index element={<BuyingCriteriaDetail />} />
      <Route path="edit" element={<BuyingCriteriaUpdate />} />
      <Route path="delete" element={<BuyingCriteriaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BuyingCriteriaRoutes;
