import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './market-segmentation-type-b-2-c-categories.reducer';

export const MarketSegmentationTypeB2cCategoriesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const marketSegmentationTypeB2cCategoriesEntity = useAppSelector(state => state.marketSegmentationTypeB2cCategories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="marketSegmentationTypeB2cCategoriesDetailsHeading">
          <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cCategories.detail.title">
            MarketSegmentationTypeB2cCategories
          </Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cCategoriesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cCategories.value">Value</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cCategoriesEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cCategories.description">Description</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cCategoriesEntity.description}</dd>
          <dt>
            <span id="placeholder">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cCategories.placeholder">Placeholder</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cCategoriesEntity.placeholder}</dd>
          <dt>
            <span id="uniqueCharacteristic">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cCategories.uniqueCharacteristic">
                Unique Characteristic
              </Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cCategoriesEntity.uniqueCharacteristic}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cCategories.language">Language</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2cCategoriesEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/market-segmentation-type-b-2-c-categories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/market-segmentation-type-b-2-c-categories/${marketSegmentationTypeB2cCategoriesEntity.id}/edit`}
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

export default MarketSegmentationTypeB2cCategoriesDetail;
