import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './buying-criteria-weighting.reducer';

export const BuyingCriteriaWeightingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const buyingCriteriaWeightingEntity = useAppSelector(state => state.buyingCriteriaWeighting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="buyingCriteriaWeightingDetailsHeading">
          <Translate contentKey="valiusaioApp.buyingCriteriaWeighting.detail.title">BuyingCriteriaWeighting</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{buyingCriteriaWeightingEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.buyingCriteriaWeighting.value">Value</Translate>
            </span>
          </dt>
          <dd>{buyingCriteriaWeightingEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.buyingCriteriaWeighting.description">Description</Translate>
            </span>
          </dt>
          <dd>{buyingCriteriaWeightingEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.buyingCriteriaWeighting.language">Language</Translate>
            </span>
          </dt>
          <dd>{buyingCriteriaWeightingEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/buying-criteria-weighting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/buying-criteria-weighting/${buyingCriteriaWeightingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BuyingCriteriaWeightingDetail;
