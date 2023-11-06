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

import { ILevelTwo } from 'app/shared/model/level-two.model';
import { getEntities, reset } from './level-two.reducer';

export const LevelTwo = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const levelTwoList = useAppSelector(state => state.levelTwo.entities);
  const loading = useAppSelector(state => state.levelTwo.loading);
  const totalItems = useAppSelector(state => state.levelTwo.totalItems);
  const links = useAppSelector(state => state.levelTwo.links);
  const entity = useAppSelector(state => state.levelTwo.entity);
  const updateSuccess = useAppSelector(state => state.levelTwo.updateSuccess);

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
      <h2 id="level-two-heading" data-cy="LevelTwoHeading">
        <Translate contentKey="valiusaioApp.levelTwo.home.title">Level Twos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="valiusaioApp.levelTwo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/level-two/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="valiusaioApp.levelTwo.home.createLabel">Create new Level Two</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={levelTwoList ? levelTwoList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {levelTwoList && levelTwoList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="valiusaioApp.levelTwo.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('identifier')}>
                    <Translate contentKey="valiusaioApp.levelTwo.identifier">Identifier</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('targetMarket')}>
                    <Translate contentKey="valiusaioApp.levelTwo.targetMarket">Target Market</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('currentMarketSegmentation')}>
                    <Translate contentKey="valiusaioApp.levelTwo.currentMarketSegmentation">Current Market Segmentation</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('segmentName')}>
                    <Translate contentKey="valiusaioApp.levelTwo.segmentName">Segment Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('marketSegmentationType')}>
                    <Translate contentKey="valiusaioApp.levelTwo.marketSegmentationType">Market Segmentation Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('uniqueCharacteristic')}>
                    <Translate contentKey="valiusaioApp.levelTwo.uniqueCharacteristic">Unique Characteristic</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('segmentDescription')}>
                    <Translate contentKey="valiusaioApp.levelTwo.segmentDescription">Segment Description</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('buyingCriteriaCategory')}>
                    <Translate contentKey="valiusaioApp.levelTwo.buyingCriteriaCategory">Buying Criteria Category</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('competitorProductName')}>
                    <Translate contentKey="valiusaioApp.levelTwo.competitorProductName">Competitor Product Name</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('competitorCompanyName')}>
                    <Translate contentKey="valiusaioApp.levelTwo.competitorCompanyName">Competitor Company Name</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('competitorBrandName')}>
                    <Translate contentKey="valiusaioApp.levelTwo.competitorBrandName">Competitor Brand Name</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('competitorProductDescription')}>
                    <Translate contentKey="valiusaioApp.levelTwo.competitorProductDescription">Competitor Product Description</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('competitorMaturityPhase')}>
                    <Translate contentKey="valiusaioApp.levelTwo.competitorMaturityPhase">Competitor Maturity Phase</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('competitorCompetitivePosition')}>
                    <Translate contentKey="valiusaioApp.levelTwo.competitorCompetitivePosition">Competitor Competitive Position</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.levelTwo.user">User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.levelTwo.buyingCriteria">Buying Criteria</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {levelTwoList.map((levelTwo, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/level-two/${levelTwo.id}`} color="link" size="sm">
                        {levelTwo.id}
                      </Button>
                    </td>
                    <td>{levelTwo.identifier}</td>
                    <td>{levelTwo.targetMarket}</td>
                    <td>{levelTwo.currentMarketSegmentation}</td>
                    <td>{levelTwo.segmentName}</td>
                    <td>{levelTwo.marketSegmentationType}</td>
                    <td>{levelTwo.uniqueCharacteristic}</td>
                    <td>{levelTwo.segmentDescription}</td>
                    <td>{levelTwo.buyingCriteriaCategory}</td>
                    <td>{levelTwo.competitorProductName}</td>
                    <td>{levelTwo.competitorCompanyName}</td>
                    <td>{levelTwo.competitorBrandName}</td>
                    <td>{levelTwo.competitorProductDescription}</td>
                    <td>{levelTwo.competitorMaturityPhase}</td>
                    <td>{levelTwo.competitorCompetitivePosition}</td>
                    <td>{levelTwo.user ? levelTwo.user.login : ''}</td>
                    <td>
                      {levelTwo.buyingCriteria ? (
                        <Link to={`/field-buying-criteria/${levelTwo.buyingCriteria.id}`}>{levelTwo.buyingCriteria.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/level-two/${levelTwo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/level-two/${levelTwo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/level-two/${levelTwo.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
                <Translate contentKey="valiusaioApp.levelTwo.home.notFound">No Level Twos found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default LevelTwo;
