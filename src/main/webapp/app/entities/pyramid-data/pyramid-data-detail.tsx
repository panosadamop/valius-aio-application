import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, openFile, byteSize } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './pyramid-data.reducer';

export const PyramidDataDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const pyramidDataEntity = useAppSelector(state => state.pyramidData.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pyramidDataDetailsHeading">
          <Translate contentKey="valiusaioApp.pyramidData.detail.title">PyramidData</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pyramidDataEntity.id}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="valiusaioApp.pyramidData.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{pyramidDataEntity.identifier}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="valiusaioApp.pyramidData.category">Category</Translate>
            </span>
          </dt>
          <dd>{pyramidDataEntity.category}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="valiusaioApp.pyramidData.value">Value</Translate>
            </span>
          </dt>
          <dd>{pyramidDataEntity.value}</dd>
          <dt>
            <span id="order">
              <Translate contentKey="valiusaioApp.pyramidData.order">Order</Translate>
            </span>
          </dt>
          <dd>{pyramidDataEntity.order}</dd>
          <dt>
            <span id="img">
              <Translate contentKey="valiusaioApp.pyramidData.img">Img</Translate>
            </span>
          </dt>
          <dd>
            {pyramidDataEntity.img ? (
              <div>
                {pyramidDataEntity.imgContentType ? (
                  <a onClick={openFile(pyramidDataEntity.imgContentType, pyramidDataEntity.img)}>
                    <img src={`data:${pyramidDataEntity.imgContentType};base64,${pyramidDataEntity.img}`} style={{ maxHeight: '30px' }} />
                  </a>
                ) : null}
                <span>
                  {pyramidDataEntity.imgContentType}, {byteSize(pyramidDataEntity.img)}
                </span>
              </div>
            ) : null}
          </dd>
        </dl>
        <Button tag={Link} to="/pyramid-data" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pyramid-data/${pyramidDataEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PyramidDataDetail;
