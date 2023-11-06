import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './population-size.reducer';

export const PopulationSizeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const populationSizeEntity = useAppSelector(state => state.populationSize.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="populationSizeDetailsHeading">
          <Translate contentKey="valiusaioApp.populationSize.detail.title">PopulationSize</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{populationSizeEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.populationSize.value">Value</Translate>
            </span>
          </dt>
          <dd>{populationSizeEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.populationSize.description">Description</Translate>
            </span>
          </dt>
          <dd>{populationSizeEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.populationSize.language">Language</Translate>
            </span>
          </dt>
          <dd>{populationSizeEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/population-size" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/population-size/${populationSizeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PopulationSizeDetail;
