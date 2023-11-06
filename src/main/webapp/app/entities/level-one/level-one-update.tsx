import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm, ValidatedBlobField } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/shared/reducers/user-management';
import { IFieldCompanyObjectives } from 'app/shared/model/field-company-objectives.model';
import { getEntities as getFieldCompanyObjectives } from 'app/entities/field-company-objectives/field-company-objectives.reducer';
import { IFieldKpi } from 'app/shared/model/field-kpi.model';
import { getEntities as getFieldKpis } from 'app/entities/field-kpi/field-kpi.reducer';
import { IFieldProductype } from 'app/shared/model/field-productype.model';
import { getEntities as getFieldProductypes } from 'app/entities/field-productype/field-productype.reducer';
import { ILevelOne } from 'app/shared/model/level-one.model';
import { getEntity, updateEntity, createEntity, reset } from './level-one.reducer';

export const LevelOneUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const users = useAppSelector(state => state.userManagement.users);
  const fieldCompanyObjectives = useAppSelector(state => state.fieldCompanyObjectives.entities);
  const fieldKpis = useAppSelector(state => state.fieldKpi.entities);
  const fieldProductypes = useAppSelector(state => state.fieldProductype.entities);
  const levelOneEntity = useAppSelector(state => state.levelOne.entity);
  const loading = useAppSelector(state => state.levelOne.loading);
  const updating = useAppSelector(state => state.levelOne.updating);
  const updateSuccess = useAppSelector(state => state.levelOne.updateSuccess);

  const handleClose = () => {
    navigate('/level-one');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }

    dispatch(getUsers({}));
    dispatch(getFieldCompanyObjectives({}));
    dispatch(getFieldKpis({}));
    dispatch(getFieldProductypes({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...levelOneEntity,
      ...values,
      user: users.find(it => it.id.toString() === values.user.toString()),
      companyObjectives: fieldCompanyObjectives.find(it => it.id.toString() === values.companyObjectives.toString()),
      kpis: fieldKpis.find(it => it.id.toString() === values.kpis.toString()),
      productType: fieldProductypes.find(it => it.id.toString() === values.productType.toString()),
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
          ...levelOneEntity,
          user: levelOneEntity?.user?.id,
          companyObjectives: levelOneEntity?.companyObjectives?.id,
          kpis: levelOneEntity?.kpis?.id,
          productType: levelOneEntity?.productType?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="valiusaioApp.levelOne.home.createOrEditLabel" data-cy="LevelOneCreateUpdateHeading">
            <Translate contentKey="valiusaioApp.levelOne.home.createOrEditLabel">Create or edit a LevelOne</Translate>
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
                  id="level-one-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.levelOne.identifier')}
                id="level-one-identifier"
                name="identifier"
                data-cy="identifier"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.companyName')}
                id="level-one-companyName"
                name="companyName"
                data-cy="companyName"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedBlobField
                label={translate('valiusaioApp.levelOne.companyLogo')}
                id="level-one-companyLogo"
                name="companyLogo"
                data-cy="companyLogo"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.brandName')}
                id="level-one-brandName"
                name="brandName"
                data-cy="brandName"
                type="text"
              />
              <ValidatedBlobField
                label={translate('valiusaioApp.levelOne.productLogo')}
                id="level-one-productLogo"
                name="productLogo"
                data-cy="productLogo"
                isImage
                accept="image/*"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.industry')}
                id="level-one-industry"
                name="industry"
                data-cy="industry"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.organizationType')}
                id="level-one-organizationType"
                name="organizationType"
                data-cy="organizationType"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.productsServices')}
                id="level-one-productsServices"
                name="productsServices"
                data-cy="productsServices"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.territory')}
                id="level-one-territory"
                name="territory"
                data-cy="territory"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.noEmployees')}
                id="level-one-noEmployees"
                name="noEmployees"
                data-cy="noEmployees"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.revenues')}
                id="level-one-revenues"
                name="revenues"
                data-cy="revenues"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.mission')}
                id="level-one-mission"
                name="mission"
                data-cy="mission"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.vision')}
                id="level-one-vision"
                name="vision"
                data-cy="vision"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.companyValues')}
                id="level-one-companyValues"
                name="companyValues"
                data-cy="companyValues"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.strategicFocus')}
                id="level-one-strategicFocus"
                name="strategicFocus"
                data-cy="strategicFocus"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.marketingBudget')}
                id="level-one-marketingBudget"
                name="marketingBudget"
                data-cy="marketingBudget"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.productDescription')}
                id="level-one-productDescription"
                name="productDescription"
                data-cy="productDescription"
                type="textarea"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.maturityPhase')}
                id="level-one-maturityPhase"
                name="maturityPhase"
                data-cy="maturityPhase"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.competitivePosition')}
                id="level-one-competitivePosition"
                name="competitivePosition"
                data-cy="competitivePosition"
                type="text"
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.targetAudienceDescription')}
                id="level-one-targetAudienceDescription"
                name="targetAudienceDescription"
                data-cy="targetAudienceDescription"
                type="textarea"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.potentialCustomersGroups')}
                id="level-one-potentialCustomersGroups"
                name="potentialCustomersGroups"
                data-cy="potentialCustomersGroups"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.strengths')}
                id="level-one-strengths"
                name="strengths"
                data-cy="strengths"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.weaknesses')}
                id="level-one-weaknesses"
                name="weaknesses"
                data-cy="weaknesses"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.opportunities')}
                id="level-one-opportunities"
                name="opportunities"
                data-cy="opportunities"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('valiusaioApp.levelOne.threats')}
                id="level-one-threats"
                name="threats"
                data-cy="threats"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField id="level-one-user" name="user" data-cy="user" label={translate('valiusaioApp.levelOne.user')} type="select">
                <option value="" key="0" />
                {users
                  ? users.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.login}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="level-one-companyObjectives"
                name="companyObjectives"
                data-cy="companyObjectives"
                label={translate('valiusaioApp.levelOne.companyObjectives')}
                type="select"
              >
                <option value="" key="0" />
                {fieldCompanyObjectives
                  ? fieldCompanyObjectives.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField id="level-one-kpis" name="kpis" data-cy="kpis" label={translate('valiusaioApp.levelOne.kpis')} type="select">
                <option value="" key="0" />
                {fieldKpis
                  ? fieldKpis.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="level-one-productType"
                name="productType"
                data-cy="productType"
                label={translate('valiusaioApp.levelOne.productType')}
                type="select"
              >
                <option value="" key="0" />
                {fieldProductypes
                  ? fieldProductypes.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/level-one" replace color="info">
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

export default LevelOneUpdate;
