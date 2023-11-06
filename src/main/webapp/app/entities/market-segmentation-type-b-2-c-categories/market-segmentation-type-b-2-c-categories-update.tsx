import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMarketSegmentationTypeB2cCategories } from 'app/shared/model/market-segmentation-type-b-2-c-categories.model';
import { Language } from 'app/shared/model/enumerations/language.model';
import { getEntity, updateEntity, createEntity, reset } from './market-segmentation-type-b-2-c-categories.reducer';

export const MarketSegmentationTypeB2cCategoriesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const marketSegmentationTypeB2cCategoriesEntity = useAppSelector(state => state.marketSegmentationTypeB2cCategories.entity);
  const loading = useAppSelector(state => state.marketSegmentationTypeB2cCategories.loading);
  const updating = useAppSelector(state => state.marketSegmentationTypeB2cCategories.updating);
  const updateSuccess = useAppSelector(state => state.marketSegmentationTypeB2cCategories.updateSuccess);
  const languageValues = Object.keys(Language);

  const handleClose = () => {
    navigate('/market-segmentation-type-b-2-c-categories');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...marketSegmentationTypeB2cCategoriesEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          language: 'ENGLISH',
          ...marketSegmentationTypeB2cCategoriesEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="valiusaioApp.marketSegmentationTypeB2cCategories.home.createOrEditLabel"
            data-cy="MarketSegmentationTypeB2cCategoriesCreateUpdateHeading"
          >
            <Translate contentKey="valiusaioApp.marketSegmentationTypeB2cCategories.home.createOrEditLabel">
              Create or edit a MarketSegmentationTypeB2cCategories
            </Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="market-segmentation-type-b-2-c-categories-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.marketSegmentationTypeB2cCategories.value')}
                id="market-segmentation-type-b-2-c-categories-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.marketSegmentationTypeB2cCategories.description')}
                id="market-segmentation-type-b-2-c-categories-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('valiusaioApp.marketSegmentationTypeB2cCategories.placeholder')}
                id="market-segmentation-type-b-2-c-categories-placeholder"
                name="placeholder"
                data-cy="placeholder"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.marketSegmentationTypeB2cCategories.uniqueCharacteristic')}
                id="market-segmentation-type-b-2-c-categories-uniqueCharacteristic"
                name="uniqueCharacteristic"
                data-cy="uniqueCharacteristic"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.marketSegmentationTypeB2cCategories.language')}
                id="market-segmentation-type-b-2-c-categories-language"
                name="language"
                data-cy="language"
                type="select"
              >
                {languageValues.map(language => (
                  <option value={language} key={language}>
                    {translate('valiusaioApp.Language.' + language)}
                  </option>
                ))}
              </ValidatedField>
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/market-segmentation-type-b-2-c-categories"
                replace
                color="info"
              >
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default MarketSegmentationTypeB2cCategoriesUpdate;
