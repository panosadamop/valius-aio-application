import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './competitive-factors.reducer';

export const CompetitiveFactorsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const competitiveFactorsEntity = useAppSelector(state => state.competitiveFactors.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="competitiveFactorsDetailsHeading">
          <Translate contentKey="valiusaioApp.competitiveFactors.detail.title">CompetitiveFactors</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{competitiveFactorsEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.competitiveFactors.value">Value</Translate>
            </span>
          </dt>
          <dd>{competitiveFactorsEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.competitiveFactors.description">Description</Translate>
            </span>
          </dt>
          <dd>{competitiveFactorsEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.competitiveFactors.language">Language</Translate>
            </span>
          </dt>
          <dd>{competitiveFactorsEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/competitive-factors" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/competitive-factors/${competitiveFactorsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompetitiveFactorsDetail;
