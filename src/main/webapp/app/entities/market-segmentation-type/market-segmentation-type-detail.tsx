import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './market-segmentation-type.reducer';

export const MarketSegmentationTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const marketSegmentationTypeEntity = useAppSelector(state => state.marketSegmentationType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="marketSegmentationTypeDetailsHeading">
          <Translate contentKey="valiusaioApp.marketSegmentationType.detail.title">MarketSegmentationType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.marketSegmentationType.value">Value</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.marketSegmentationType.description">Description</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.marketSegmentationType.language">Language</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/market-segmentation-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/market-segmentation-type/${marketSegmentationTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MarketSegmentationTypeDetail;
