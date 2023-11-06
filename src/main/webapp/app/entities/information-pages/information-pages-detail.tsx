import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './information-pages.reducer';

export const InformationPagesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const informationPagesEntity = useAppSelector(state => state.informationPages.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="informationPagesDetailsHeading">
          <Translate contentKey="valiusaioApp.informationPages.detail.title">InformationPages</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{informationPagesEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="valiusaioApp.informationPages.title">Title</Translate>
            </span>
          </dt>
          <dd>{informationPagesEntity.title}</dd>
          <dt>
            <span id="subTitle">
              <Translate contentKey="valiusaioApp.informationPages.subTitle">Sub Title</Translate>
            </span>
          </dt>
          <dd>{informationPagesEntity.subTitle}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.informationPages.description">Description</Translate>
            </span>
          </dt>
          <dd>{informationPagesEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/information-pages" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/information-pages/${informationPagesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InformationPagesDetail;
