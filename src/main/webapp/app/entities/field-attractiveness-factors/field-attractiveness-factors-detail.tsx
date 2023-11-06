import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field-attractiveness-factors.reducer';

export const FieldAttractivenessFactorsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldAttractivenessFactorsEntity = useAppSelector(state => state.fieldAttractivenessFactors.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldAttractivenessFactorsDetailsHeading">
          <Translate contentKey="valiusaioApp.fieldAttractivenessFactors.detail.title">FieldAttractivenessFactors</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldAttractivenessFactorsEntity.id}</dd>
          <dt>
            <span id="attractivenessFactors">
              <Translate contentKey="valiusaioApp.fieldAttractivenessFactors.attractivenessFactors">Attractiveness Factors</Translate>
            </span>
          </dt>
          <dd>{fieldAttractivenessFactorsEntity.attractivenessFactors}</dd>
        </dl>
        <Button tag={Link} to="/field-attractiveness-factors" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-attractiveness-factors/${fieldAttractivenessFactorsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldAttractivenessFactorsDetail;
