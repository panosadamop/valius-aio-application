import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './industry.reducer';

export const IndustryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const industryEntity = useAppSelector(state => state.industry.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="industryDetailsHeading">
          <Translate contentKey="valiusaioApp.industry.detail.title">Industry</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{industryEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.industry.value">Value</Translate>
            </span>
          </dt>
          <dd>{industryEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.industry.description">Description</Translate>
            </span>
          </dt>
          <dd>{industryEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.industry.language">Language</Translate>
            </span>
          </dt>
          <dd>{industryEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/industry" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/industry/${industryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default IndustryDetail;
