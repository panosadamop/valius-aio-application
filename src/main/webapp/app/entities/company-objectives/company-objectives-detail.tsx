import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './company-objectives.reducer';

export const CompanyObjectivesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const companyObjectivesEntity = useAppSelector(state => state.companyObjectives.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="companyObjectivesDetailsHeading">
          <Translate contentKey="valiusaioApp.companyObjectives.detail.title">CompanyObjectives</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{companyObjectivesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.companyObjectives.value">Value</Translate>
            </span>
          </dt>
          <dd>{companyObjectivesEntity.value}</dd>
          <dt>
            <span id="placeholder">
              <Translate contentKey="valiusaioApp.companyObjectives.placeholder">Placeholder</Translate>
            </span>
          </dt>
          <dd>{companyObjectivesEntity.placeholder}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.companyObjectives.description">Description</Translate>
            </span>
          </dt>
          <dd>{companyObjectivesEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.companyObjectives.language">Language</Translate>
            </span>
          </dt>
          <dd>{companyObjectivesEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/company-objectives" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/company-objectives/${companyObjectivesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CompanyObjectivesDetail;
