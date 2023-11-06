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

import { ILevelThree } from 'app/shared/model/level-three.model';
import { getEntities, reset } from './level-three.reducer';

export const LevelThree = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const levelThreeList = useAppSelector(state => state.levelThree.entities);
  const loading = useAppSelector(state => state.levelThree.loading);
  const totalItems = useAppSelector(state => state.levelThree.totalItems);
  const links = useAppSelector(state => state.levelThree.links);
  const entity = useAppSelector(state => state.levelThree.entity);
  const updateSuccess = useAppSelector(state => state.levelThree.updateSuccess);

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
      <h2 id="level-three-heading" data-cy="LevelThreeHeading">
        <Translate contentKey="valiusaioApp.levelThree.home.title">Level Threes</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="valiusaioApp.levelThree.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/level-three/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="valiusaioApp.levelThree.home.createLabel">Create new Level Three</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={levelThreeList ? levelThreeList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {levelThreeList && levelThreeList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="valiusaioApp.levelThree.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('identifier')}>
                    <Translate contentKey="valiusaioApp.levelThree.identifier">Identifier</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('mafCategory')}>
                    <Translate contentKey="valiusaioApp.levelThree.mafCategory">Maf Category</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('weightingMaf')}>
                    <Translate contentKey="valiusaioApp.levelThree.weightingMaf">Weighting Maf</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('lowAttractivenessRangeMaf')}>
                    <Translate contentKey="valiusaioApp.levelThree.lowAttractivenessRangeMaf">Low Attractiveness Range Maf</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('mediumAttractivenessRangeMaf')}>
                    <Translate contentKey="valiusaioApp.levelThree.mediumAttractivenessRangeMaf">Medium Attractiveness Range Maf</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('highAttractivenessRangeMaf')}>
                    <Translate contentKey="valiusaioApp.levelThree.highAttractivenessRangeMaf">High Attractiveness Range Maf</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('segmentScoreMaf')}>
                    <Translate contentKey="valiusaioApp.levelThree.segmentScoreMaf">Segment Score Maf</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.levelThree.user">User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.levelThree.attractivenessFactors">Attractiveness Factors</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {levelThreeList.map((levelThree, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/level-three/${levelThree.id}`} color="link" size="sm">
                        {levelThree.id}
                      </Button>
                    </td>
                    <td>{levelThree.identifier}</td>
                    <td>{levelThree.mafCategory}</td>
                    <td>{levelThree.weightingMaf}</td>
                    <td>{levelThree.lowAttractivenessRangeMaf}</td>
                    <td>{levelThree.mediumAttractivenessRangeMaf}</td>
                    <td>{levelThree.highAttractivenessRangeMaf}</td>
                    <td>{levelThree.segmentScoreMaf}</td>
                    <td>{levelThree.user ? levelThree.user.login : ''}</td>
                    <td>
                      {levelThree.attractivenessFactors ? (
                        <Link to={`/field-attractiveness-factors/${levelThree.attractivenessFactors.id}`}>
                          {levelThree.attractivenessFactors.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/level-three/${levelThree.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/level-three/${levelThree.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/level-three/${levelThree.id}/delete`}
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
                <Translate contentKey="valiusaioApp.levelThree.home.notFound">No Level Threes found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default LevelThree;
