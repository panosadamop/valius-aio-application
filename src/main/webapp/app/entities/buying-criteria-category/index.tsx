import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import BuyingCriteriaCategory from './buying-criteria-category';
import BuyingCriteriaCategoryDetail from './buying-criteria-category-detail';
import BuyingCriteriaCategoryUpdate from './buying-criteria-category-update';
import BuyingCriteriaCategoryDeleteDialog from './buying-criteria-category-delete-dialog';

const BuyingCriteriaCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<BuyingCriteriaCategory />} />
    <Route path="new" element={<BuyingCriteriaCategoryUpdate />} />
    <Route path=":id">
      <Route index element={<BuyingCriteriaCategoryDetail />} />
      <Route path="edit" element={<BuyingCriteriaCategoryUpdate />} />
      <Route path="delete" element={<BuyingCriteriaCategoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BuyingCriteriaCategoryRoutes;
