import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import SocialFactors from './social-factors';
import SocialFactorsDetail from './social-factors-detail';
import SocialFactorsUpdate from './social-factors-update';
import SocialFactorsDeleteDialog from './social-factors-delete-dialog';

const SocialFactorsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<SocialFactors />} />
    <Route path="new" element={<SocialFactorsUpdate />} />
    <Route path=":id">
      <Route index element={<SocialFactorsDetail />} />
      <Route path="edit" element={<SocialFactorsUpdate />} />
      <Route path="delete" element={<SocialFactorsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SocialFactorsRoutes;
