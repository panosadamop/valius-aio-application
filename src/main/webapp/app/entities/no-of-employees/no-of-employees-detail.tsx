import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './no-of-employees.reducer';

export const NoOfEmployeesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const noOfEmployeesEntity = useAppSelector(state => state.noOfEmployees.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="noOfEmployeesDetailsHeading">
          <Translate contentKey="valiusaioApp.noOfEmployees.detail.title">NoOfEmployees</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{noOfEmployeesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.noOfEmployees.value">Value</Translate>
            </span>
          </dt>
          <dd>{noOfEmployeesEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.noOfEmployees.description">Description</Translate>
            </span>
          </dt>
          <dd>{noOfEmployeesEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.noOfEmployees.language">Language</Translate>
            </span>
          </dt>
          <dd>{noOfEmployeesEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/no-of-employees" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/no-of-employees/${noOfEmployeesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NoOfEmployeesDetail;
