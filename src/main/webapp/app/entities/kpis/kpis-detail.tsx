import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './kpis.reducer';

export const KpisDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const kpisEntity = useAppSelector(state => state.kpis.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="kpisDetailsHeading">
          <Translate contentKey="valiusaioApp.kpis.detail.title">Kpis</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{kpisEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.kpis.value">Value</Translate>
            </span>
          </dt>
          <dd>{kpisEntity.value}</dd>
          <dt>
            <span id="checkBoxValue">
              <Translate contentKey="valiusaioApp.kpis.checkBoxValue">Check Box Value</Translate>
            </span>
          </dt>
          <dd>{kpisEntity.checkBoxValue ? 'true' : 'false'}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.kpis.description">Description</Translate>
            </span>
          </dt>
          <dd>{kpisEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.kpis.language">Language</Translate>
            </span>
          </dt>
          <dd>{kpisEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/kpis" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/kpis/${kpisEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default KpisDetail;
