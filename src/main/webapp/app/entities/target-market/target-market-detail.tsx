import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './target-market.reducer';

export const TargetMarketDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const targetMarketEntity = useAppSelector(state => state.targetMarket.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="targetMarketDetailsHeading">
          <Translate contentKey="valiusaioApp.targetMarket.detail.title">TargetMarket</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{targetMarketEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.targetMarket.value">Value</Translate>
            </span>
          </dt>
          <dd>{targetMarketEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.targetMarket.description">Description</Translate>
            </span>
          </dt>
          <dd>{targetMarketEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.targetMarket.language">Language</Translate>
            </span>
          </dt>
          <dd>{targetMarketEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/target-market" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/target-market/${targetMarketEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TargetMarketDetail;
