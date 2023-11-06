import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import OrganisationType from './organisation-type';
import OrganisationTypeDetail from './organisation-type-detail';
import OrganisationTypeUpdate from './organisation-type-update';
import OrganisationTypeDeleteDialog from './organisation-type-delete-dialog';

const OrganisationTypeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<OrganisationType />} />
    <Route path="new" element={<OrganisationTypeUpdate />} />
    <Route path=":id">
      <Route index element={<OrganisationTypeDetail />} />
      <Route path="edit" element={<OrganisationTypeUpdate />} />
      <Route path="delete" element={<OrganisationTypeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OrganisationTypeRoutes;
