import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './revenues.reducer';

export const RevenuesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const revenuesEntity = useAppSelector(state => state.revenues.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="revenuesDetailsHeading">
          <Translate contentKey="valiusaioApp.revenues.detail.title">Revenues</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{revenuesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.revenues.value">Value</Translate>
            </span>
          </dt>
          <dd>{revenuesEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.revenues.description">Description</Translate>
            </span>
          </dt>
          <dd>{revenuesEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.revenues.language">Language</Translate>
            </span>
          </dt>
          <dd>{revenuesEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/revenues" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/revenues/${revenuesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default RevenuesDetail;
