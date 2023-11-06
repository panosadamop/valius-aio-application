import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './preferred-communication-channel.reducer';

export const PreferredCommunicationChannelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const preferredCommunicationChannelEntity = useAppSelector(state => state.preferredCommunicationChannel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="preferredCommunicationChannelDetailsHeading">
          <Translate contentKey="valiusaioApp.preferredCommunicationChannel.detail.title">PreferredCommunicationChannel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{preferredCommunicationChannelEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.preferredCommunicationChannel.value">Value</Translate>
            </span>
          </dt>
          <dd>{preferredCommunicationChannelEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.preferredCommunicationChannel.description">Description</Translate>
            </span>
          </dt>
          <dd>{preferredCommunicationChannelEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.preferredCommunicationChannel.language">Language</Translate>
            </span>
          </dt>
          <dd>{preferredCommunicationChannelEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/preferred-communication-channel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/preferred-communication-channel/${preferredCommunicationChannelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PreferredCommunicationChannelDetail;
