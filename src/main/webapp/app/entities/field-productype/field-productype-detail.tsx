import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field-productype.reducer';

export const FieldProductypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldProductypeEntity = useAppSelector(state => state.fieldProductype.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldProductypeDetailsHeading">
          <Translate contentKey="valiusaioApp.fieldProductype.detail.title">FieldProductype</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldProductypeEntity.id}</dd>
          <dt>
            <span id="productType">
              <Translate contentKey="valiusaioApp.fieldProductype.productType">Product Type</Translate>
            </span>
          </dt>
          <dd>{fieldProductypeEntity.productType}</dd>
        </dl>
        <Button tag={Link} to="/field-productype" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-productype/${fieldProductypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldProductypeDetail;
