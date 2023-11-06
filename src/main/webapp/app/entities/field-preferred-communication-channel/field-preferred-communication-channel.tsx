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

import { IFieldPreferredCommunicationChannel } from 'app/shared/model/field-preferred-communication-channel.model';
import { getEntities, reset } from './field-preferred-communication-channel.reducer';

export const FieldPreferredCommunicationChannel = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );
  const [sorting, setSorting] = useState(false);

  const fieldPreferredCommunicationChannelList = useAppSelector(state => state.fieldPreferredCommunicationChannel.entities);
  const loading = useAppSelector(state => state.fieldPreferredCommunicationChannel.loading);
  const totalItems = useAppSelector(state => state.fieldPreferredCommunicationChannel.totalItems);
  const links = useAppSelector(state => state.fieldPreferredCommunicationChannel.links);
  const entity = useAppSelector(state => state.fieldPreferredCommunicationChannel.entity);
  const updateSuccess = useAppSelector(state => state.fieldPreferredCommunicationChannel.updateSuccess);

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
      <h2 id="field-preferred-communication-channel-heading" data-cy="FieldPreferredCommunicationChannelHeading">
        <Translate contentKey="valiusaioApp.fieldPreferredCommunicationChannel.home.title">
          Field Preferred Communication Channels
        </Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="valiusaioApp.fieldPreferredCommunicationChannel.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link
            to="/field-preferred-communication-channel/new"
            className="btn btn-primary jh-create-entity"
            id="jh-create-entity"
            data-cy="entityCreateButton"
          >
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="valiusaioApp.fieldPreferredCommunicationChannel.home.createLabel">
              Create new Field Preferred Communication Channel
            </Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={fieldPreferredCommunicationChannelList ? fieldPreferredCommunicationChannelList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {fieldPreferredCommunicationChannelList && fieldPreferredCommunicationChannelList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="valiusaioApp.fieldPreferredCommunicationChannel.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th className="hand" onClick={sort('preferredCommunicationChannel')}>
                    <Translate contentKey="valiusaioApp.fieldPreferredCommunicationChannel.preferredCommunicationChannel">
                      Preferred Communication Channel
                    </Translate>{' '}
                    <FontAwesomeIcon icon="sort" />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {fieldPreferredCommunicationChannelList.map((fieldPreferredCommunicationChannel, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button
                        tag={Link}
                        to={`/field-preferred-communication-channel/${fieldPreferredCommunicationChannel.id}`}
                        color="link"
                        size="sm"
                      >
                        {fieldPreferredCommunicationChannel.id}
                      </Button>
                    </td>
                    <td>{fieldPreferredCommunicationChannel.preferredCommunicationChannel}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button
                          tag={Link}
                          to={`/field-preferred-communication-channel/${fieldPreferredCommunicationChannel.id}`}
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
                          to={`/field-preferred-communication-channel/${fieldPreferredCommunicationChannel.id}/edit`}
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
                          to={`/field-preferred-communication-channel/${fieldPreferredCommunicationChannel.id}/delete`}
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
                <Translate contentKey="valiusaioApp.fieldPreferredCommunicationChannel.home.notFound">
                  No Field Preferred Communication Channels found
                </Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default FieldPreferredCommunicationChannel;
