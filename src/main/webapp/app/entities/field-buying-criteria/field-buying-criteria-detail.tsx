import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field-buying-criteria.reducer';

export const FieldBuyingCriteriaDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldBuyingCriteriaEntity = useAppSelector(state => state.fieldBuyingCriteria.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldBuyingCriteriaDetailsHeading">
          <Translate contentKey="valiusaioApp.fieldBuyingCriteria.detail.title">FieldBuyingCriteria</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldBuyingCriteriaEntity.id}</dd>
          <dt>
            <span id="buyingCriteria">
              <Translate contentKey="valiusaioApp.fieldBuyingCriteria.buyingCriteria">Buying Criteria</Translate>
            </span>
          </dt>
          <dd>{fieldBuyingCriteriaEntity.buyingCriteria}</dd>
        </dl>
        <Button tag={Link} to="/field-buying-criteria" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-buying-criteria/${fieldBuyingCriteriaEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldBuyingCriteriaDetail;
