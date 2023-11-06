import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import FieldAttractivenessFactors from './field-attractiveness-factors';
import FieldAttractivenessFactorsDetail from './field-attractiveness-factors-detail';
import FieldAttractivenessFactorsUpdate from './field-attractiveness-factors-update';
import FieldAttractivenessFactorsDeleteDialog from './field-attractiveness-factors-delete-dialog';

const FieldAttractivenessFactorsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<FieldAttractivenessFactors />} />
    <Route path="new" element={<FieldAttractivenessFactorsUpdate />} />
    <Route path=":id">
      <Route index element={<FieldAttractivenessFactorsDetail />} />
      <Route path="edit" element={<FieldAttractivenessFactorsUpdate />} />
      <Route path="delete" element={<FieldAttractivenessFactorsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FieldAttractivenessFactorsRoutes;
