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

import { ISurvey } from 'app/shared/model/survey.model';
import { getEntities, reset } from './survey.reducer';

export const Survey = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const surveyList = useAppSelector(state => state.survey.entities);
  const loading = useAppSelector(state => state.survey.loading);
  const totalItems = useAppSelector(state => state.survey.totalItems);
  const links = useAppSelector(state => state.survey.links);
  const entity = useAppSelector(state => state.survey.entity);
  const updateSuccess = useAppSelector(state => state.survey.updateSuccess);

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
      <h2 id="survey-heading" data-cy="SurveyHeading">
        <Translate contentKey="valiusaioApp.survey.home.title">Surveys</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="valiusaioApp.survey.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/survey/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="valiusaioApp.survey.home.createLabel">Create new Survey</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={surveyList ? surveyList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {surveyList && surveyList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="valiusaioApp.survey.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('consumerAssessedBrands')}>
                    <Translate contentKey="valiusaioApp.survey.consumerAssessedBrands">Consumer Assessed Brands</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('criticalSuccessFactors')}>
                    <Translate contentKey="valiusaioApp.survey.criticalSuccessFactors">Critical Success Factors</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('additionalBuyingCriteria')}>
                    <Translate contentKey="valiusaioApp.survey.additionalBuyingCriteria">Additional Buying Criteria</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('consumerSegmentGroup')}>
                    <Translate contentKey="valiusaioApp.survey.consumerSegmentGroup">Consumer Segment Group</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('segmentCsf')}>
                    <Translate contentKey="valiusaioApp.survey.segmentCsf">Segment Csf</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('gender')}>
                    <Translate contentKey="valiusaioApp.survey.gender">Gender</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('ageGroup')}>
                    <Translate contentKey="valiusaioApp.survey.ageGroup">Age Group</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('location')}>
                    <Translate contentKey="valiusaioApp.survey.location">Location</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('education')}>
                    <Translate contentKey="valiusaioApp.survey.education">Education</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('occupation')}>
                    <Translate contentKey="valiusaioApp.survey.occupation">Occupation</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('netPromoterScore')}>
                    <Translate contentKey="valiusaioApp.survey.netPromoterScore">Net Promoter Score</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.survey.user">User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.survey.buyingCriteriaWeighting">Buying Criteria Weighting</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.survey.preferredPurchaseChannel">Preferred Purchase Channel</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.survey.preferredCommunicationChannel">Preferred Communication Channel</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {surveyList.map((survey, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/survey/${survey.id}`} color="link" size="sm">
                        {survey.id}
                      </Button>
                    </td>
                    <td>{survey.consumerAssessedBrands}</td>
                    <td>{survey.criticalSuccessFactors}</td>
                    <td>{survey.additionalBuyingCriteria}</td>
                    <td>{survey.consumerSegmentGroup}</td>
                    <td>{survey.segmentCsf}</td>
                    <td>{survey.gender}</td>
                    <td>{survey.ageGroup}</td>
                    <td>{survey.location}</td>
                    <td>{survey.education}</td>
                    <td>{survey.occupation}</td>
                    <td>{survey.netPromoterScore}</td>
                    <td>{survey.user ? survey.user.login : ''}</td>
                    <td>
                      {survey.buyingCriteriaWeighting ? (
                        <Link to={`/field-buying-criteria-weighting/${survey.buyingCriteriaWeighting.id}`}>
                          {survey.buyingCriteriaWeighting.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {survey.preferredPurchaseChannel ? (
                        <Link to={`/field-preferred-purchase-channel/${survey.preferredPurchaseChannel.id}`}>
                          {survey.preferredPurchaseChannel.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>
                      {survey.preferredCommunicationChannel ? (
                        <Link to={`/field-preferred-communication-channel/${survey.preferredCommunicationChannel.id}`}>
                          {survey.preferredCommunicationChannel.id}
                        </Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/survey/${survey.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/survey/${survey.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/survey/${survey.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
                <Translate contentKey="valiusaioApp.survey.home.notFound">No Surveys found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default Survey;
