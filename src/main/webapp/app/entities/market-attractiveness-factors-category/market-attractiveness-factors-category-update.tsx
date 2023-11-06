import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IMarketAttractivenessFactorsCategory } from 'app/shared/model/market-attractiveness-factors-category.model';
import { Language } from 'app/shared/model/enumerations/language.model';
import { getEntity, updateEntity, createEntity, reset } from './market-attractiveness-factors-category.reducer';

export const MarketAttractivenessFactorsCategoryUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const marketAttractivenessFactorsCategoryEntity = useAppSelector(state => state.marketAttractivenessFactorsCategory.entity);
  const loading = useAppSelector(state => state.marketAttractivenessFactorsCategory.loading);
  const updating = useAppSelector(state => state.marketAttractivenessFactorsCategory.updating);
  const updateSuccess = useAppSelector(state => state.marketAttractivenessFactorsCategory.updateSuccess);
  const languageValues = Object.keys(Language);

  const handleClose = () => {
    navigate('/market-attractiveness-factors-category');
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
      ...marketAttractivenessFactorsCategoryEntity,
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
          ...marketAttractivenessFactorsCategoryEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="valiusaioApp.marketAttractivenessFactorsCategory.home.createOrEditLabel"
            data-cy="MarketAttractivenessFactorsCategoryCreateUpdateHeading"
          >
            <Translate contentKey="valiusaioApp.marketAttractivenessFactorsCategory.home.createOrEditLabel">
              Create or edit a MarketAttractivenessFactorsCategory
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
                  id="market-attractiveness-factors-category-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.marketAttractivenessFactorsCategory.value')}
                id="market-attractiveness-factors-category-value"
                name="value"
                data-cy="value"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.marketAttractivenessFactorsCategory.tab')}
                id="market-attractiveness-factors-category-tab"
                name="tab"
                data-cy="tab"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.marketAttractivenessFactorsCategory.description')}
                id="market-attractiveness-factors-category-description"
                name="description"
                data-cy="description"
                type="textarea"
              />
              <ValidatedField
                label={translate('valiusaioApp.marketAttractivenessFactorsCategory.language')}
                id="market-attractiveness-factors-category-language"
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
                to="/market-attractiveness-factors-category"
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

export default MarketAttractivenessFactorsCategoryUpdate;
