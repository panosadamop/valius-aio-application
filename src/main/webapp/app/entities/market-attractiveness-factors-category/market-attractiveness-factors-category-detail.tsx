import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './market-attractiveness-factors-category.reducer';

export const MarketAttractivenessFactorsCategoryDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const marketAttractivenessFactorsCategoryEntity = useAppSelector(state => state.marketAttractivenessFactorsCategory.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="marketAttractivenessFactorsCategoryDetailsHeading">
          <Translate contentKey="valiusaioApp.marketAttractivenessFactorsCategory.detail.title">
            MarketAttractivenessFactorsCategory
          </Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{marketAttractivenessFactorsCategoryEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.marketAttractivenessFactorsCategory.value">Value</Translate>
            </span>
          </dt>
          <dd>{marketAttractivenessFactorsCategoryEntity.value}</dd>
          <dt>
            <span id="tab">
              <Translate contentKey="valiusaioApp.marketAttractivenessFactorsCategory.tab">Tab</Translate>
            </span>
          </dt>
          <dd>{marketAttractivenessFactorsCategoryEntity.tab}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.marketAttractivenessFactorsCategory.description">Description</Translate>
            </span>
          </dt>
          <dd>{marketAttractivenessFactorsCategoryEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.marketAttractivenessFactorsCategory.language">Language</Translate>
            </span>
          </dt>
          <dd>{marketAttractivenessFactorsCategoryEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/market-attractiveness-factors-category" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/market-attractiveness-factors-category/${marketAttractivenessFactorsCategoryEntity.id}/edit`}
          replace
          color="primary"
        >
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default MarketAttractivenessFactorsCategoryDetail;
