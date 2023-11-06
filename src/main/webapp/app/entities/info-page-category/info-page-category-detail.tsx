import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './info-page-category.reducer';

export const InfoPageCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const infoPageCategoryEntity = useAppSelector(state => state.infoPageCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="infoPageCategoryDetailsHeading">
          <Translate contentKey="valiusaioApp.infoPageCategory.detail.title">InfoPageCategory</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{infoPageCategoryEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.infoPageCategory.value">Value</Translate>
            </span>
          </dt>
          <dd>{infoPageCategoryEntity.value}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.infoPageCategory.language">Language</Translate>
            </span>
          </dt>
          <dd>{infoPageCategoryEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/info-page-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/info-page-category/${infoPageCategoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InfoPageCategoryDetail;
