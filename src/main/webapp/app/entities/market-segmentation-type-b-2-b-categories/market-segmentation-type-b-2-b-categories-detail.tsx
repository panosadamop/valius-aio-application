import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './market-segmentation-type-b-2-b-categories.reducer';

export const MarketSegmentationTypeB2bCategoriesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const marketSegmentationTypeB2bCategoriesEntity = useAppSelector(state => state.marketSegmentationTypeB2bCategories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="marketSegmentationTypeB2bCategoriesDetailsHeading">
          <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.detail.title">
            MarketSegmentationTypeB2bCategories
          </Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bCategoriesEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.value">Value</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bCategoriesEntity.value}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.description">Description</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bCategoriesEntity.description}</dd>
          <dt>
            <span id="placeholder">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.placeholder">Placeholder</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bCategoriesEntity.placeholder}</dd>
          <dt>
            <span id="uniqueCharacteristic">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.uniqueCharacteristic">
                Unique Characteristic
              </Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bCategoriesEntity.uniqueCharacteristic}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.language">Language</Translate>
            </span>
          </dt>
          <dd>{marketSegmentationTypeB2bCategoriesEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/market-segmentation-type-b-2-b-categories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button
          tag={Link}
          to={`/market-segmentation-type-b-2-b-categories/${marketSegmentationTypeB2bCategoriesEntity.id}/edit`}
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

export default MarketSegmentationTypeB2bCategoriesDetail;
