import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './level-three.reducer';

export const LevelThreeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const levelThreeEntity = useAppSelector(state => state.levelThree.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="levelThreeDetailsHeading">
          <Translate contentKey="valiusaioApp.levelThree.detail.title">LevelThree</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{levelThreeEntity.id}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="valiusaioApp.levelThree.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{levelThreeEntity.identifier}</dd>
          <dt>
            <span id="mafCategory">
              <Translate contentKey="valiusaioApp.levelThree.mafCategory">Maf Category</Translate>
            </span>
          </dt>
          <dd>{levelThreeEntity.mafCategory}</dd>
          <dt>
            <span id="weightingMaf">
              <Translate contentKey="valiusaioApp.levelThree.weightingMaf">Weighting Maf</Translate>
            </span>
          </dt>
          <dd>{levelThreeEntity.weightingMaf}</dd>
          <dt>
            <span id="lowAttractivenessRangeMaf">
              <Translate contentKey="valiusaioApp.levelThree.lowAttractivenessRangeMaf">Low Attractiveness Range Maf</Translate>
            </span>
          </dt>
          <dd>{levelThreeEntity.lowAttractivenessRangeMaf}</dd>
          <dt>
            <span id="mediumAttractivenessRangeMaf">
              <Translate contentKey="valiusaioApp.levelThree.mediumAttractivenessRangeMaf">Medium Attractiveness Range Maf</Translate>
            </span>
          </dt>
          <dd>{levelThreeEntity.mediumAttractivenessRangeMaf}</dd>
          <dt>
            <span id="highAttractivenessRangeMaf">
              <Translate contentKey="valiusaioApp.levelThree.highAttractivenessRangeMaf">High Attractiveness Range Maf</Translate>
            </span>
          </dt>
          <dd>{levelThreeEntity.highAttractivenessRangeMaf}</dd>
          <dt>
            <span id="segmentScoreMaf">
              <Translate contentKey="valiusaioApp.levelThree.segmentScoreMaf">Segment Score Maf</Translate>
            </span>
          </dt>
          <dd>{levelThreeEntity.segmentScoreMaf}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.levelThree.user">User</Translate>
          </dt>
          <dd>{levelThreeEntity.user ? levelThreeEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.levelThree.attractivenessFactors">Attractiveness Factors</Translate>
          </dt>
          <dd>{levelThreeEntity.attractivenessFactors ? levelThreeEntity.attractivenessFactors.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/level-three" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/level-three/${levelThreeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LevelThreeDetail;
