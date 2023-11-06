import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './buying-criteria.reducer';

export const BuyingCriteriaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const buyingCriteriaEntity = useAppSelector(state => state.buyingCriteria.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="buyingCriteriaDetailsHeading">
          <Translate contentKey="valiusaioApp.buyingCriteria.detail.title">BuyingCriteria</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{buyingCriteriaEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.buyingCriteria.value">Value</Translate>
            </span>
          </dt>
          <dd>{buyingCriteriaEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.buyingCriteria.description">Description</Translate>
            </span>
          </dt>
          <dd>{buyingCriteriaEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.buyingCriteria.language">Language</Translate>
            </span>
          </dt>
          <dd>{buyingCriteriaEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/buying-criteria" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/buying-criteria/${buyingCriteriaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default BuyingCriteriaDetail;
