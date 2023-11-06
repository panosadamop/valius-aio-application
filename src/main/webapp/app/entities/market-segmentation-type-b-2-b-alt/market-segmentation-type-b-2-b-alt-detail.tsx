import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './market-segmentation-type-b-2-b-alt.reducer';

export const MarketSegmentationTypeB2bAltDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const marketSegmentationTypeB2bAltEntity = useAppSelector(state => state.marketSegmentationTypeB2bAlt.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="marketSegmentationTypeB2bAltDetailsHeading">
          <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bAlt.detail.title">MarketSegmentationTypeB2bAlt</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bAltEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bAlt.value">Value</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bAltEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bAlt.description">Description</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bAltEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bAlt.language">Language</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bAltEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/market-segmentation-type-b-2-b-alt" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/market-segmentation-type-b-2-b-alt/${marketSegmentationTypeB2bAltEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MarketSegmentationTypeB2bAltDetail;
