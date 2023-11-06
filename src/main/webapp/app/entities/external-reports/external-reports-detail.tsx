import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './external-reports.reducer';

export const ExternalReportsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const externalReportsEntity = useAppSelector(state => state.externalReports.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="externalReportsDetailsHeading">
          <Translate contentKey="valiusaioApp.externalReports.detail.title">ExternalReports</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{externalReportsEntity.id}</dd>
          <dt>
            <span id="reportUrl">
              <Translate contentKey="valiusaioApp.externalReports.reportUrl">Report Url</Translate>
            </span>
          </dt>
          <dd>{externalReportsEntity.reportUrl}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.externalReports.description">Description</Translate>
            </span>
          </dt>
          <dd>{externalReportsEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/external-reports" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/external-reports/${externalReportsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ExternalReportsDetail;
