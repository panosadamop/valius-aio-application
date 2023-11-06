import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field-preferred-purchase-channel.reducer';

export const FieldPreferredPurchaseChannelDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldPreferredPurchaseChannelEntity = useAppSelector(state => state.fieldPreferredPurchaseChannel.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldPreferredPurchaseChannelDetailsHeading">
          <Translate contentKey="valiusaioApp.fieldPreferredPurchaseChannel.detail.title">FieldPreferredPurchaseChannel</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldPreferredPurchaseChannelEntity.id}</dd>
          <dt>
            <span id="preferredPurchaseChannel">
              <Translate contentKey="valiusaioApp.fieldPreferredPurchaseChannel.preferredPurchaseChannel">
                Preferred Purchase Channel
              </Translate>
            </span>
          </dt>
          <dd>{fieldPreferredPurchaseChannelEntity.preferredPurchaseChannel}</dd>
        </dl>
        <Button tag={Link} to="/field-preferred-purchase-channel" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-preferred-purchase-channel/${fieldPreferredPurchaseChannelEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldPreferredPurchaseChannelDetail;
