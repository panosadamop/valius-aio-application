import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './field-kpi.reducer';

export const FieldKpiDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const fieldKpiEntity = useAppSelector(state => state.fieldKpi.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="fieldKpiDetailsHeading">
          <Translate contentKey="valiusaioApp.fieldKpi.detail.title">FieldKpi</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{fieldKpiEntity.id}</dd>
          <dt>
            <span id="kpis">
              <Translate contentKey="valiusaioApp.fieldKpi.kpis">Kpis</Translate>
            </span>
          </dt>
          <dd>{fieldKpiEntity.kpis}</dd>
        </dl>
        <Button tag={Link} to="/field-kpi" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/field-kpi/${fieldKpiEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default FieldKpiDetail;
