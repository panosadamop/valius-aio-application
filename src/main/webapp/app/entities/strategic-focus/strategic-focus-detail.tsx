import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './strategic-focus.reducer';

export const StrategicFocusDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const strategicFocusEntity = useAppSelector(state => state.strategicFocus.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="strategicFocusDetailsHeading">
          <Translate contentKey="valiusaioApp.strategicFocus.detail.title">StrategicFocus</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{strategicFocusEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.strategicFocus.value">Value</Translate>
            </span>
          </dt>
          <dd>{strategicFocusEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.strategicFocus.description">Description</Translate>
            </span>
          </dt>
          <dd>{strategicFocusEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.strategicFocus.language">Language</Translate>
            </span>
          </dt>
          <dd>{strategicFocusEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/strategic-focus" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/strategic-focus/${strategicFocusEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default StrategicFocusDetail;
