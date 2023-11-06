import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './social-factors.reducer';

export const SocialFactorsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const socialFactorsEntity = useAppSelector(state => state.socialFactors.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="socialFactorsDetailsHeading">
          <Translate contentKey="valiusaioApp.socialFactors.detail.title">SocialFactors</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{socialFactorsEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.socialFactors.value">Value</Translate>
            </span>
          </dt>
          <dd>{socialFactorsEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.socialFactors.description">Description</Translate>
            </span>
          </dt>
          <dd>{socialFactorsEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.socialFactors.language">Language</Translate>
            </span>
          </dt>
          <dd>{socialFactorsEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/social-factors" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/social-factors/${socialFactorsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SocialFactorsDetail;
