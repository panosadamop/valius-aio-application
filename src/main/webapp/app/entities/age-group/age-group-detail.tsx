import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './age-group.reducer';

export const AgeGroupDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const ageGroupEntity = useAppSelector(state => state.ageGroup.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="ageGroupDetailsHeading">
          <Translate contentKey="valiusaioApp.ageGroup.detail.title">AgeGroup</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{ageGroupEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.ageGroup.value">Value</Translate>
            </span>
          </dt>
          <dd>{ageGroupEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.ageGroup.description">Description</Translate>
            </span>
          </dt>
          <dd>{ageGroupEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.ageGroup.language">Language</Translate>
            </span>
          </dt>
          <dd>{ageGroupEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/age-group" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/age-group/${ageGroupEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default AgeGroupDetail;
