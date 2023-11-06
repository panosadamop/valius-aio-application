import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './market-segmentation-type-b-2-b.reducer';

export const MarketSegmentationTypeB2bDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const marketSegmentationTypeB2bEntity = useAppSelector(state => state.marketSegmentationTypeB2b.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="marketSegmentationTypeB2bDetailsHeading">
          <Translate contentKey="valiusaioApp.marketSegmentationTypeB2b.detail.title">MarketSegmentationTypeB2b</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2b.value">Value</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2b.description">Description</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2b.language">Language</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/market-segmentation-type-b-2-b" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/market-segmentation-type-b-2-b/${marketSegmentationTypeB2bEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MarketSegmentationTypeB2bDetail;
