import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field-preferred-communication-channel.reducer';

export const FieldPreferredCommunicationChannelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldPreferredCommunicationChannelEntity = useAppSelector(state => state.fieldPreferredCommunicationChannel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldPreferredCommunicationChannelDetailsHeading">
          <Translate contentKey="valiusaioApp.fieldPreferredCommunicationChannel.detail.title">
            FieldPreferredCommunicationChannel
          </Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldPreferredCommunicationChannelEntity.id}</dd>
          <dt>
            <span id="preferredCommunicationChannel">
              <Translate contentKey="valiusaioApp.fieldPreferredCommunicationChannel.preferredCommunicationChannel">
                Preferred Communication Channel
              </Translate>
            </span>
          </dt>
          <dd>{fieldPreferredCommunicationChannelEntity.preferredCommunicationChannel}</dd>
        </dl>
        <Button tag={Link} to="/field-preferred-communication-channel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/field-preferred-communication-channel/${fieldPreferredCommunicationChannelEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldPreferredCommunicationChannelDetail;
