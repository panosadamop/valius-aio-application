import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './attractiveness-factors.reducer';

export const AttractivenessFactorsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const attractivenessFactorsEntity = useAppSelector(state => state.attractivenessFactors.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="attractivenessFactorsDetailsHeading">
          <Translate contentKey="valiusaioApp.attractivenessFactors.detail.title">AttractivenessFactors</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{attractivenessFactorsEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.attractivenessFactors.value">Value</Translate>
            </span>
          </dt>
          <dd>{attractivenessFactorsEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.attractivenessFactors.description">Description</Translate>
            </span>
          </dt>
          <dd>{attractivenessFactorsEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.attractivenessFactors.language">Language</Translate>
            </span>
          </dt>
          <dd>{attractivenessFactorsEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/attractiveness-factors" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/attractiveness-factors/${attractivenessFactorsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AttractivenessFactorsDetail;
