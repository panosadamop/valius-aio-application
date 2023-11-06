import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './segment-unique-characteristic.reducer';

export const SegmentUniqueCharacteristicDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const segmentUniqueCharacteristicEntity = useAppSelector(state => state.segmentUniqueCharacteristic.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="segmentUniqueCharacteristicDetailsHeading">
          <Translate contentKey="valiusaioApp.segmentUniqueCharacteristic.detail.title">SegmentUniqueCharacteristic</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{segmentUniqueCharacteristicEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.segmentUniqueCharacteristic.value">Value</Translate>
            </span>
          </dt>
          <dd>{segmentUniqueCharacteristicEntity.value}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="valiusaioApp.segmentUniqueCharacteristic.category">Category</Translate>
            </span>
          </dt>
          <dd>{segmentUniqueCharacteristicEntity.category}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.segmentUniqueCharacteristic.description">Description</Translate>
            </span>
          </dt>
          <dd>{segmentUniqueCharacteristicEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.segmentUniqueCharacteristic.language">Language</Translate>
            </span>
          </dt>
          <dd>{segmentUniqueCharacteristicEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/segment-unique-characteristic" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/segment-unique-characteristic/${segmentUniqueCharacteristicEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SegmentUniqueCharacteristicDetail;
