import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFieldBuyingCriteriaWeighting } from 'app/shared/model/field-buying-criteria-weighting.model';
import { getEntities, reset } from './field-buying-criteria-weighting.reducer';

export const FieldBuyingCriteriaWeighting = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const fieldBuyingCriteriaWeightingList = useAppSelector(state => state.fieldBuyingCriteriaWeighting.entities);
  const loading = useAppSelector(state => state.fieldBuyingCriteriaWeighting.loading);
  const totalItems = useAppSelector(state => state.fieldBuyingCriteriaWeighting.totalItems);
  const links = useAppSelector(state => state.fieldBuyingCriteriaWeighting.links);
  const entity = useAppSelector(state => state.fieldBuyingCriteriaWeighting.entity);
  const updateSuccess = useAppSelector(state => state.fieldBuyingCriteriaWeighting.updateSuccess);

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
      <h2 id="field-buying-criteria-weighting-heading" data-cy="FieldBuyingCriteriaWeightingHeading">
        <Translate contentKey="valiusaioApp.fieldBuyingCriteriaWeighting.home.title">Field Buying Criteria Weightings</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="valiusaioApp.fieldBuyingCriteriaWeighting.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/field-buying-criteria-weighting/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="valiusaioApp.fieldBuyingCriteriaWeighting.home.createLabel">
              Create new Field Buying Criteria Weighting
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={fieldBuyingCriteriaWeightingList ? fieldBuyingCriteriaWeightingList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {fieldBuyingCriteriaWeightingList && fieldBuyingCriteriaWeightingList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="valiusaioApp.fieldBuyingCriteriaWeighting.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('buyingCriteriaWeighting')}>
                    <Translate contentKey="valiusaioApp.fieldBuyingCriteriaWeighting.buyingCriteriaWeighting">
                      Buying Criteria Weighting
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {fieldBuyingCriteriaWeightingList.map((fieldBuyingCriteriaWeighting, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/field-buying-criteria-weighting/${fieldBuyingCriteriaWeighting.id}`} color="link" size="sm">
                        {fieldBuyingCriteriaWeighting.id}
                      </Button>
                    </td>
                    <td>{fieldBuyingCriteriaWeighting.buyingCriteriaWeighting}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/field-buying-criteria-weighting/${fieldBuyingCriteriaWeighting.id}`}
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
                          to={`/field-buying-criteria-weighting/${fieldBuyingCriteriaWeighting.id}/edit`}
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
                          to={`/field-buying-criteria-weighting/${fieldBuyingCriteriaWeighting.id}/delete`}
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
                <Translate contentKey="valiusaioApp.fieldBuyingCriteriaWeighting.home.notFound">
                  No Field Buying Criteria Weightings found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FieldBuyingCriteriaWeighting;
