import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './market-segmentation-type-b-2-c-alt.reducer';

export const MarketSegmentationTypeB2cAltDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const marketSegmentationTypeB2cAltEntity = useAppSelector(state => state.marketSegmentationTypeB2cAlt.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="marketSegmentationTypeB2cAltDetailsHeading">
          <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cAlt.detail.title">MarketSegmentationTypeB2cAlt</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cAltEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cAlt.value">Value</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cAltEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cAlt.description">Description</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cAltEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cAlt.language">Language</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cAltEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/market-segmentation-type-b-2-c-alt" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/market-segmentation-type-b-2-c-alt/${marketSegmentationTypeB2cAltEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MarketSegmentationTypeB2cAltDetail;
