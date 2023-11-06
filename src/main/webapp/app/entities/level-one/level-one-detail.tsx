import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './level-one.reducer';

export const LevelOneDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const levelOneEntity = useAppSelector(state => state.levelOne.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="levelOneDetailsHeading">
          <Translate contentKey="valiusaioApp.levelOne.detail.title">LevelOne</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.id}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="valiusaioApp.levelOne.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.identifier}</dd>
          <dt>
            <span id="companyName">
              <Translate contentKey="valiusaioApp.levelOne.companyName">Company Name</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.companyName}</dd>
          <dt>
            <span id="companyLogo">
              <Translate contentKey="valiusaioApp.levelOne.companyLogo">Company Logo</Translate>
            </span>
          </dt>
          <dd>
            {levelOneEntity.companyLogo ? (
              <div>
                {levelOneEntity.companyLogoContentType ? (
                  <a onClick={openFile(levelOneEntity.companyLogoContentType, levelOneEntity.companyLogo)}>
                    <img
                      src={`data:${levelOneEntity.companyLogoContentType};base64,${levelOneEntity.companyLogo}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {levelOneEntity.companyLogoContentType}, {byteSize(levelOneEntity.companyLogo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="brandName">
              <Translate contentKey="valiusaioApp.levelOne.brandName">Brand Name</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.brandName}</dd>
          <dt>
            <span id="productLogo">
              <Translate contentKey="valiusaioApp.levelOne.productLogo">Product Logo</Translate>
            </span>
          </dt>
          <dd>
            {levelOneEntity.productLogo ? (
              <div>
                {levelOneEntity.productLogoContentType ? (
                  <a onClick={openFile(levelOneEntity.productLogoContentType, levelOneEntity.productLogo)}>
                    <img
                      src={`data:${levelOneEntity.productLogoContentType};base64,${levelOneEntity.productLogo}`}
                      style={{ maxHeight: '30px' }}
                    />
                  </a>
                ) : null}
                <span>
                  {levelOneEntity.productLogoContentType}, {byteSize(levelOneEntity.productLogo)}
                </span>
              </div>
            ) : null}
          </dd>
          <dt>
            <span id="industry">
              <Translate contentKey="valiusaioApp.levelOne.industry">Industry</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.industry}</dd>
          <dt>
            <span id="organizationType">
              <Translate contentKey="valiusaioApp.levelOne.organizationType">Organization Type</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.organizationType}</dd>
          <dt>
            <span id="productsServices">
              <Translate contentKey="valiusaioApp.levelOne.productsServices">Products Services</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.productsServices}</dd>
          <dt>
            <span id="territory">
              <Translate contentKey="valiusaioApp.levelOne.territory">Territory</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.territory}</dd>
          <dt>
            <span id="noEmployees">
              <Translate contentKey="valiusaioApp.levelOne.noEmployees">No Employees</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.noEmployees}</dd>
          <dt>
            <span id="revenues">
              <Translate contentKey="valiusaioApp.levelOne.revenues">Revenues</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.revenues}</dd>
          <dt>
            <span id="mission">
              <Translate contentKey="valiusaioApp.levelOne.mission">Mission</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.mission}</dd>
          <dt>
            <span id="vision">
              <Translate contentKey="valiusaioApp.levelOne.vision">Vision</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.vision}</dd>
          <dt>
            <span id="companyValues">
              <Translate contentKey="valiusaioApp.levelOne.companyValues">Company Values</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.companyValues}</dd>
          <dt>
            <span id="strategicFocus">
              <Translate contentKey="valiusaioApp.levelOne.strategicFocus">Strategic Focus</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.strategicFocus}</dd>
          <dt>
            <span id="marketingBudget">
              <Translate contentKey="valiusaioApp.levelOne.marketingBudget">Marketing Budget</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.marketingBudget}</dd>
          <dt>
            <span id="productDescription">
              <Translate contentKey="valiusaioApp.levelOne.productDescription">Product Description</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.productDescription}</dd>
          <dt>
            <span id="maturityPhase">
              <Translate contentKey="valiusaioApp.levelOne.maturityPhase">Maturity Phase</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.maturityPhase}</dd>
          <dt>
            <span id="competitivePosition">
              <Translate contentKey="valiusaioApp.levelOne.competitivePosition">Competitive Position</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.competitivePosition}</dd>
          <dt>
            <span id="targetAudienceDescription">
              <Translate contentKey="valiusaioApp.levelOne.targetAudienceDescription">Target Audience Description</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.targetAudienceDescription}</dd>
          <dt>
            <span id="potentialCustomersGroups">
              <Translate contentKey="valiusaioApp.levelOne.potentialCustomersGroups">Potential Customers Groups</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.potentialCustomersGroups}</dd>
          <dt>
            <span id="strengths">
              <Translate contentKey="valiusaioApp.levelOne.strengths">Strengths</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.strengths}</dd>
          <dt>
            <span id="weaknesses">
              <Translate contentKey="valiusaioApp.levelOne.weaknesses">Weaknesses</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.weaknesses}</dd>
          <dt>
            <span id="opportunities">
              <Translate contentKey="valiusaioApp.levelOne.opportunities">Opportunities</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.opportunities}</dd>
          <dt>
            <span id="threats">
              <Translate contentKey="valiusaioApp.levelOne.threats">Threats</Translate>
            </span>
          </dt>
          <dd>{levelOneEntity.threats}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.levelOne.user">User</Translate>
          </dt>
          <dd>{levelOneEntity.user ? levelOneEntity.user.login : ''}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.levelOne.companyObjectives">Company Objectives</Translate>
          </dt>
          <dd>{levelOneEntity.companyObjectives ? levelOneEntity.companyObjectives.id : ''}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.levelOne.kpis">Kpis</Translate>
          </dt>
          <dd>{levelOneEntity.kpis ? levelOneEntity.kpis.id : ''}</dd>
          <dt>
            <Translate contentKey="valiusaioApp.levelOne.productType">Product Type</Translate>
          </dt>
          <dd>{levelOneEntity.productType ? levelOneEntity.productType.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/level-one" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/level-one/${levelOneEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default LevelOneDetail;
