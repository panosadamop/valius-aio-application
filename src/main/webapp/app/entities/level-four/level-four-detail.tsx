import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './level-four.reducer';

export const LevelFourDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const levelFourEntity = useAppSelector(state => state.levelFour.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="levelFourDetailsHeading">
          <Translate contentKey="valiusaioApp.levelFour.detail.title">LevelFour</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{levelFourEntity.id}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="valiusaioApp.levelFour.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{levelFourEntity.identifier}</dd>
          <dt>
            <span id="criticalSuccessFactors">
              <Translate contentKey="valiusaioApp.levelFour.criticalSuccessFactors">Critical Success Factors</Translate>
            </span>
          </dt>
          <dd>{levelFourEntity.criticalSuccessFactors}</dd>
          <dt>
            <span id="populationSize">
              <Translate contentKey="valiusaioApp.levelFour.populationSize">Population Size</Translate>
            </span>
          </dt>
          <dd>{levelFourEntity.populationSize}</dd>
          <dt>
            <span id="statisticalError">
              <Translate contentKey="valiusaioApp.levelFour.statisticalError">Statistical Error</Translate>
            </span>
          </dt>
          <dd>{levelFourEntity.statisticalError}</dd>
          <dt>
            <span id="confidenceLevel">
              <Translate contentKey="valiusaioApp.levelFour.confidenceLevel">Confidence Level</Translate>
            </span>
          </dt>
          <dd>{levelFourEntity.confidenceLevel}</dd>
          <dt>
            <span id="requiredSampleSize">
              <Translate contentKey="valiusaioApp.levelFour.requiredSampleSize">Required Sample Size</Translate>
            </span>
          </dt>
          <dd>{levelFourEntity.requiredSampleSize}</dd>
          <dt>
            <span id="estimatedResponseRate">
              <Translate contentKey="valiusaioApp.levelFour.estimatedResponseRate">Estimated Response Rate</Translate>
            </span>
          </dt>
          <dd>{levelFourEntity.estimatedResponseRate}</dd>
          <dt>
            <span id="surveyParticipantsNumber">
              <Translate contentKey="valiusaioApp.levelFour.surveyParticipantsNumber">Survey Participants Number</Translate>
            </span>
          </dt>
          <dd>{levelFourEntity.surveyParticipantsNumber}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.levelFour.user">User</Translate>
          </dt>
          <dd>{levelFourEntity.user ? levelFourEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/level-four" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/level-four/${levelFourEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LevelFourDetail;
