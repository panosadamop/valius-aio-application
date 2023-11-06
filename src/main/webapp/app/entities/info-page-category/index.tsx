import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import InfoPageCategory from './info-page-category';
import InfoPageCategoryDetail from './info-page-category-detail';
import InfoPageCategoryUpdate from './info-page-category-update';
import InfoPageCategoryDeleteDialog from './info-page-category-delete-dialog';

const InfoPageCategoryRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<InfoPageCategory />} />
    <Route path="new" element={<InfoPageCategoryUpdate />} />
    <Route path=":id">
      <Route index element={<InfoPageCategoryDetail />} />
      <Route path="edit" element={<InfoPageCategoryUpdate />} />
      <Route path="delete" element={<InfoPageCategoryDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InfoPageCategoryRoutes;
