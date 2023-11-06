import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field-buying-criteria-weighting.reducer';

export const FieldBuyingCriteriaWeightingDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldBuyingCriteriaWeightingEntity = useAppSelector(state => state.fieldBuyingCriteriaWeighting.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldBuyingCriteriaWeightingDetailsHeading">
          <Translate contentKey="valiusaioApp.fieldBuyingCriteriaWeighting.detail.title">FieldBuyingCriteriaWeighting</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldBuyingCriteriaWeightingEntity.id}</dd>
          <dt>
            <span id="buyingCriteriaWeighting">
              <Translate contentKey="valiusaioApp.fieldBuyingCriteriaWeighting.buyingCriteriaWeighting">
                Buying Criteria Weighting
              </Translate>
            </span>
          </dt>
          <dd>{fieldBuyingCriteriaWeightingEntity.buyingCriteriaWeighting}</dd>
        </dl>
        <Button tag={Link} to="/field-buying-criteria-weighting" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-buying-criteria-weighting/${fieldBuyingCriteriaWeightingEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldBuyingCriteriaWeightingDetail;
