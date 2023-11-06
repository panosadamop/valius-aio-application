import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import MarketingBudget from './marketing-budget';
import MarketingBudgetDetail from './marketing-budget-detail';
import MarketingBudgetUpdate from './marketing-budget-update';
import MarketingBudgetDeleteDialog from './marketing-budget-delete-dialog';

const MarketingBudgetRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<MarketingBudget />} />
    <Route path="new" element={<MarketingBudgetUpdate />} />
    <Route path=":id">
      <Route index element={<MarketingBudgetDetail />} />
      <Route path="edit" element={<MarketingBudgetUpdate />} />
      <Route path="delete" element={<MarketingBudgetDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MarketingBudgetRoutes;
