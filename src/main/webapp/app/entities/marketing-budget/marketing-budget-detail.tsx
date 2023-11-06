import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './marketing-budget.reducer';

export const MarketingBudgetDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const marketingBudgetEntity = useAppSelector(state => state.marketingBudget.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="marketingBudgetDetailsHeading">
          <Translate contentKey="valiusaioApp.marketingBudget.detail.title">MarketingBudget</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{marketingBudgetEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.marketingBudget.value">Value</Translate>
            </span>
          </dt>
          <dd>{marketingBudgetEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.marketingBudget.description">Description</Translate>
            </span>
          </dt>
          <dd>{marketingBudgetEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.marketingBudget.language">Language</Translate>
            </span>
          </dt>
          <dd>{marketingBudgetEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/marketing-budget" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/marketing-budget/${marketingBudgetEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MarketingBudgetDetail;
