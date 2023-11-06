import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './territory.reducer';

export const TerritoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const territoryEntity = useAppSelector(state => state.territory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="territoryDetailsHeading">
          <Translate contentKey="valiusaioApp.territory.detail.title">Territory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{territoryEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.territory.value">Value</Translate>
            </span>
          </dt>
          <dd>{territoryEntity.value}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.territory.language">Language</Translate>
            </span>
          </dt>
          <dd>{territoryEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/territory" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/territory/${territoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TerritoryDetail;
