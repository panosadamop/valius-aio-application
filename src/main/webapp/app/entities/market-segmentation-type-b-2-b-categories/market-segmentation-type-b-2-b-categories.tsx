import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMarketSegmentationTypeB2bCategories } from 'app/shared/model/market-segmentation-type-b-2-b-categories.model';
import { getEntities, reset } from './market-segmentation-type-b-2-b-categories.reducer';

export const MarketSegmentationTypeB2bCategories = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const marketSegmentationTypeB2bCategoriesList = useAppSelector(state => state.marketSegmentationTypeB2bCategories.entities);
  const loading = useAppSelector(state => state.marketSegmentationTypeB2bCategories.loading);
  const totalItems = useAppSelector(state => state.marketSegmentationTypeB2bCategories.totalItems);
  const links = useAppSelector(state => state.marketSegmentationTypeB2bCategories.links);
  const entity = useAppSelector(state => state.marketSegmentationTypeB2bCategories.entity);
  const updateSuccess = useAppSelector(state => state.marketSegmentationTypeB2bCategories.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  return (
    <div>
      <h2 id="market-segmentation-type-b-2-b-categories-heading" data-cy="MarketSegmentationTypeB2bCategoriesHeading">
        <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.home.title">
          Market Segmentation Type B 2 B Categories
        </Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/market-segmentation-type-b-2-b-categories/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.home.createLabel">
              Create new Market Segmentation Type B 2 B Categories
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={marketSegmentationTypeB2bCategoriesList ? marketSegmentationTypeB2bCategoriesList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {marketSegmentationTypeB2bCategoriesList && marketSegmentationTypeB2bCategoriesList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('value')}>
                    <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.value">Value</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('description')}>
                    <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.description">Description</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('placeholder')}>
                    <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.placeholder">Placeholder</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('uniqueCharacteristic')}>
                    <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.uniqueCharacteristic">
                      Unique Characteristic
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('language')}>
                    <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.language">Language</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {marketSegmentationTypeB2bCategoriesList.map((marketSegmentationTypeB2bCategories, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button
                        tag={Link}
                        to={`/market-segmentation-type-b-2-b-categories/${marketSegmentationTypeB2bCategories.id}`}
                        color="link"
                        size="sm"
                      >
                        {marketSegmentationTypeB2bCategories.id}
                      </Button>
                    </td>
                    <td>{marketSegmentationTypeB2bCategories.value}</td>
                    <td>{marketSegmentationTypeB2bCategories.description}</td>
                    <td>{marketSegmentationTypeB2bCategories.placeholder}</td>
                    <td>{marketSegmentationTypeB2bCategories.uniqueCharacteristic}</td>
                    <td>
                      <Translate contentKey={`valiusaioApp.Language.${marketSegmentationTypeB2bCategories.language}`} />
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/market-segmentation-type-b-2-b-categories/${marketSegmentationTypeB2bCategories.id}`}
                          color="info"
                          size="sm"
                          data-cy="entityDetailsButton"
                        >
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/market-segmentation-type-b-2-b-categories/${marketSegmentationTypeB2bCategories.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/market-segmentation-type-b-2-b-categories/${marketSegmentationTypeB2bCategories.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.home.notFound">
                  No Market Segmentation Type B 2 B Categories found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default MarketSegmentationTypeB2bCategories;
