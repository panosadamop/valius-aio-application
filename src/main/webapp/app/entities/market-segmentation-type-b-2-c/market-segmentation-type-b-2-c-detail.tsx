import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './market-segmentation-type-b-2-c.reducer';

export const MarketSegmentationTypeB2cDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const marketSegmentationTypeB2cEntity = useAppSelector(state => state.marketSegmentationTypeB2c.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="marketSegmentationTypeB2cDetailsHeading">
          <Translate contentKey="valiusaioApp.marketSegmentationTypeB2c.detail.title">MarketSegmentationTypeB2c</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2c.value">Value</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2c.description">Description</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2c.language">Language</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/market-segmentation-type-b-2-c" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/market-segmentation-type-b-2-c/${marketSegmentationTypeB2cEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MarketSegmentationTypeB2cDetail;
