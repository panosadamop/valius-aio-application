import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './education.reducer';

export const EducationDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const educationEntity = useAppSelector(state => state.education.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="educationDetailsHeading">
          <Translate contentKey="valiusaioApp.education.detail.title">Education</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{educationEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.education.value">Value</Translate>
            </span>
          </dt>
          <dd>{educationEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.education.description">Description</Translate>
            </span>
          </dt>
          <dd>{educationEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.education.language">Language</Translate>
            </span>
          </dt>
          <dd>{educationEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/education" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/education/${educationEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default EducationDetail;
