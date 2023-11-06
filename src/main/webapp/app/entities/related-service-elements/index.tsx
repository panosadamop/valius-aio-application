import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import RelatedServiceElements from './related-service-elements';
import RelatedServiceElementsDetail from './related-service-elements-detail';
import RelatedServiceElementsUpdate from './related-service-elements-update';
import RelatedServiceElementsDeleteDialog from './related-service-elements-delete-dialog';

const RelatedServiceElementsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<RelatedServiceElements />} />
    <Route path="new" element={<RelatedServiceElementsUpdate />} />
    <Route path=":id">
      <Route index element={<RelatedServiceElementsDetail />} />
      <Route path="edit" element={<RelatedServiceElementsUpdate />} />
      <Route path="delete" element={<RelatedServiceElementsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RelatedServiceElementsRoutes;
