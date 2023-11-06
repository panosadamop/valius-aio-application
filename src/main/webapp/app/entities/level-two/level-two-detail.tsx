import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './level-two.reducer';

export const LevelTwoDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const levelTwoEntity = useAppSelector(state => state.levelTwo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="levelTwoDetailsHeading">
          <Translate contentKey="valiusaioApp.levelTwo.detail.title">LevelTwo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.id}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="valiusaioApp.levelTwo.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.identifier}</dd>
          <dt>
            <span id="targetMarket">
              <Translate contentKey="valiusaioApp.levelTwo.targetMarket">Target Market</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.targetMarket}</dd>
          <dt>
            <span id="currentMarketSegmentation">
              <Translate contentKey="valiusaioApp.levelTwo.currentMarketSegmentation">Current Market Segmentation</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.currentMarketSegmentation}</dd>
          <dt>
            <span id="segmentName">
              <Translate contentKey="valiusaioApp.levelTwo.segmentName">Segment Name</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.segmentName}</dd>
          <dt>
            <span id="marketSegmentationType">
              <Translate contentKey="valiusaioApp.levelTwo.marketSegmentationType">Market Segmentation Type</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.marketSegmentationType}</dd>
          <dt>
            <span id="uniqueCharacteristic">
              <Translate contentKey="valiusaioApp.levelTwo.uniqueCharacteristic">Unique Characteristic</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.uniqueCharacteristic}</dd>
          <dt>
            <span id="segmentDescription">
              <Translate contentKey="valiusaioApp.levelTwo.segmentDescription">Segment Description</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.segmentDescription}</dd>
          <dt>
            <span id="buyingCriteriaCategory">
              <Translate contentKey="valiusaioApp.levelTwo.buyingCriteriaCategory">Buying Criteria Category</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.buyingCriteriaCategory}</dd>
          <dt>
            <span id="competitorProductName">
              <Translate contentKey="valiusaioApp.levelTwo.competitorProductName">Competitor Product Name</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.competitorProductName}</dd>
          <dt>
            <span id="competitorCompanyName">
              <Translate contentKey="valiusaioApp.levelTwo.competitorCompanyName">Competitor Company Name</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.competitorCompanyName}</dd>
          <dt>
            <span id="competitorBrandName">
              <Translate contentKey="valiusaioApp.levelTwo.competitorBrandName">Competitor Brand Name</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.competitorBrandName}</dd>
          <dt>
            <span id="competitorProductDescription">
              <Translate contentKey="valiusaioApp.levelTwo.competitorProductDescription">Competitor Product Description</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.competitorProductDescription}</dd>
          <dt>
            <span id="competitorMaturityPhase">
              <Translate contentKey="valiusaioApp.levelTwo.competitorMaturityPhase">Competitor Maturity Phase</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.competitorMaturityPhase}</dd>
          <dt>
            <span id="competitorCompetitivePosition">
              <Translate contentKey="valiusaioApp.levelTwo.competitorCompetitivePosition">Competitor Competitive Position</Translate>
            </span>
          </dt>
          <dd>{levelTwoEntity.competitorCompetitivePosition}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.levelTwo.user">User</Translate>
          </dt>
          <dd>{levelTwoEntity.user ? levelTwoEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.levelTwo.buyingCriteria">Buying Criteria</Translate>
          </dt>
          <dd>{levelTwoEntity.buyingCriteria ? levelTwoEntity.buyingCriteria.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/level-two" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/level-two/${levelTwoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LevelTwoDetail;
