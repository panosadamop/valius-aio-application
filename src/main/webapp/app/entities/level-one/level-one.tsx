import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { openFile, byteSize, Translate, getSortState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ILevelOne } from 'app/shared/model/level-one.model';
import { getEntities, reset } from './level-one.reducer';

export const LevelOne = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const levelOneList = useAppSelector(state => state.levelOne.entities);
  const loading = useAppSelector(state => state.levelOne.loading);
  const totalItems = useAppSelector(state => state.levelOne.totalItems);
  const links = useAppSelector(state => state.levelOne.links);
  const entity = useAppSelector(state => state.levelOne.entity);
  const updateSuccess = useAppSelector(state => state.levelOne.updateSuccess);

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
      <h2 id="level-one-heading" data-cy="LevelOneHeading">
        <Translate contentKey="valiusaioApp.levelOne.home.title">Level Ones</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="valiusaioApp.levelOne.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/level-one/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="valiusaioApp.levelOne.home.createLabel">Create new Level One</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={levelOneList ? levelOneList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {levelOneList && levelOneList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="valiusaioApp.levelOne.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('identifier')}>
                    <Translate contentKey="valiusaioApp.levelOne.identifier">Identifier</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('companyName')}>
                    <Translate contentKey="valiusaioApp.levelOne.companyName">Company Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('companyLogo')}>
                    <Translate contentKey="valiusaioApp.levelOne.companyLogo">Company Logo</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('brandName')}>
                    <Translate contentKey="valiusaioApp.levelOne.brandName">Brand Name</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('productLogo')}>
                    <Translate contentKey="valiusaioApp.levelOne.productLogo">Product Logo</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('industry')}>
                    <Translate contentKey="valiusaioApp.levelOne.industry">Industry</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('organizationType')}>
                    <Translate contentKey="valiusaioApp.levelOne.organizationType">Organization Type</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('productsServices')}>
                    <Translate contentKey="valiusaioApp.levelOne.productsServices">Products Services</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('territory')}>
                    <Translate contentKey="valiusaioApp.levelOne.territory">Territory</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('noEmployees')}>
                    <Translate contentKey="valiusaioApp.levelOne.noEmployees">No Employees</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('revenues')}>
                    <Translate contentKey="valiusaioApp.levelOne.revenues">Revenues</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('mission')}>
                    <Translate contentKey="valiusaioApp.levelOne.mission">Mission</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('vision')}>
                    <Translate contentKey="valiusaioApp.levelOne.vision">Vision</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('companyValues')}>
                    <Translate contentKey="valiusaioApp.levelOne.companyValues">Company Values</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('strategicFocus')}>
                    <Translate contentKey="valiusaioApp.levelOne.strategicFocus">Strategic Focus</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('marketingBudget')}>
                    <Translate contentKey="valiusaioApp.levelOne.marketingBudget">Marketing Budget</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('productDescription')}>
                    <Translate contentKey="valiusaioApp.levelOne.productDescription">Product Description</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('maturityPhase')}>
                    <Translate contentKey="valiusaioApp.levelOne.maturityPhase">Maturity Phase</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('competitivePosition')}>
                    <Translate contentKey="valiusaioApp.levelOne.competitivePosition">Competitive Position</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('targetAudienceDescription')}>
                    <Translate contentKey="valiusaioApp.levelOne.targetAudienceDescription">Target Audience Description</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('potentialCustomersGroups')}>
                    <Translate contentKey="valiusaioApp.levelOne.potentialCustomersGroups">Potential Customers Groups</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('strengths')}>
                    <Translate contentKey="valiusaioApp.levelOne.strengths">Strengths</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('weaknesses')}>
                    <Translate contentKey="valiusaioApp.levelOne.weaknesses">Weaknesses</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('opportunities')}>
                    <Translate contentKey="valiusaioApp.levelOne.opportunities">Opportunities</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('threats')}>
                    <Translate contentKey="valiusaioApp.levelOne.threats">Threats</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.levelOne.user">User</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.levelOne.companyObjectives">Company Objectives</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.levelOne.kpis">Kpis</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th>
                    <Translate contentKey="valiusaioApp.levelOne.productType">Product Type</Translate> <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {levelOneList.map((levelOne, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/level-one/${levelOne.id}`} color="link" size="sm">
                        {levelOne.id}
                      </Button>
                    </td>
                    <td>{levelOne.identifier}</td>
                    <td>{levelOne.companyName}</td>
                    <td>
                      {levelOne.companyLogo ? (
                        <div>
                          {levelOne.companyLogoContentType ? (
                            <a onClick={openFile(levelOne.companyLogoContentType, levelOne.companyLogo)}>
                              <img
                                src={`data:${levelOne.companyLogoContentType};base64,${levelOne.companyLogo}`}
                                style={{ maxHeight: '30px' }}
                              />
                              &nbsp;
                            </a>
                          ) : null}
                          <span>
                            {levelOne.companyLogoContentType}, {byteSize(levelOne.companyLogo)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{levelOne.brandName}</td>
                    <td>
                      {levelOne.productLogo ? (
                        <div>
                          {levelOne.productLogoContentType ? (
                            <a onClick={openFile(levelOne.productLogoContentType, levelOne.productLogo)}>
                              <img
                                src={`data:${levelOne.productLogoContentType};base64,${levelOne.productLogo}`}
                                style={{ maxHeight: '30px' }}
                              />
                              &nbsp;
                            </a>
                          ) : null}
                          <span>
                            {levelOne.productLogoContentType}, {byteSize(levelOne.productLogo)}
                          </span>
                        </div>
                      ) : null}
                    </td>
                    <td>{levelOne.industry}</td>
                    <td>{levelOne.organizationType}</td>
                    <td>{levelOne.productsServices}</td>
                    <td>{levelOne.territory}</td>
                    <td>{levelOne.noEmployees}</td>
                    <td>{levelOne.revenues}</td>
                    <td>{levelOne.mission}</td>
                    <td>{levelOne.vision}</td>
                    <td>{levelOne.companyValues}</td>
                    <td>{levelOne.strategicFocus}</td>
                    <td>{levelOne.marketingBudget}</td>
                    <td>{levelOne.productDescription}</td>
                    <td>{levelOne.maturityPhase}</td>
                    <td>{levelOne.competitivePosition}</td>
                    <td>{levelOne.targetAudienceDescription}</td>
                    <td>{levelOne.potentialCustomersGroups}</td>
                    <td>{levelOne.strengths}</td>
                    <td>{levelOne.weaknesses}</td>
                    <td>{levelOne.opportunities}</td>
                    <td>{levelOne.threats}</td>
                    <td>{levelOne.user ? levelOne.user.login : ''}</td>
                    <td>
                      {levelOne.companyObjectives ? (
                        <Link to={`/field-company-objectives/${levelOne.companyObjectives.id}`}>{levelOne.companyObjectives.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td>{levelOne.kpis ? <Link to={`/field-kpi/${levelOne.kpis.id}`}>{levelOne.kpis.id}</Link> : ''}</td>
                    <td>
                      {levelOne.productType ? (
                        <Link to={`/field-productype/${levelOne.productType.id}`}>{levelOne.productType.id}</Link>
                      ) : (
                        ''
                      )}
                    </td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/level-one/${levelOne.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/level-one/${levelOne.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button tag={Link} to={`/level-one/${levelOne.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
                <Translate contentKey="valiusaioApp.levelOne.home.notFound">No Level Ones found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default LevelOne;
