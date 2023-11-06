import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './product-type.reducer';

export const ProductTypeDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const productTypeEntity = useAppSelector(state => state.productType.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="productTypeDetailsHeading">
          <Translate contentKey="valiusaioApp.productType.detail.title">ProductType</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{productTypeEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.productType.value">Value</Translate>
            </span>
          </dt>
          <dd>{productTypeEntity.value}</dd>
          <dt>
            <span id="checkBoxValue">
              <Translate contentKey="valiusaioApp.productType.checkBoxValue">Check Box Value</Translate>
            </span>
          </dt>
          <dd>{productTypeEntity.checkBoxValue ? 'true' : 'false'}</dd>
          <dt>
            <span id="description">
              <Translate contentKey="valiusaioApp.productType.description">Description</Translate>
            </span>
          </dt>
          <dd>{productTypeEntity.description}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="valiusaioApp.productType.language">Language</Translate>
            </span>
          </dt>
          <dd>{productTypeEntity.language}</dd>
        </dl>
        <Button tag={Link} to="/product-type" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/product-type/${productTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProductTypeDetail;
