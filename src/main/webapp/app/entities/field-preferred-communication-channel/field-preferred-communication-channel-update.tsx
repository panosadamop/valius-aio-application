import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IFieldPreferredCommunicationChannel } from 'app/shared/model/field-preferred-communication-channel.model';
import { getEntity, updateEntity, createEntity, reset } from './field-preferred-communication-channel.reducer';

export const FieldPreferredCommunicationChannelUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const fieldPreferredCommunicationChannelEntity = useAppSelector(state => state.fieldPreferredCommunicationChannel.entity);
  const loading = useAppSelector(state => state.fieldPreferredCommunicationChannel.loading);
  const updating = useAppSelector(state => state.fieldPreferredCommunicationChannel.updating);
  const updateSuccess = useAppSelector(state => state.fieldPreferredCommunicationChannel.updateSuccess);

  const handleClose = () => {
    navigate('/field-preferred-communication-channel');
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
      ...fieldPreferredCommunicationChannelEntity,
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
          ...fieldPreferredCommunicationChannelEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2
            id="valiusaioApp.fieldPreferredCommunicationChannel.home.createOrEditLabel"
            data-cy="FieldPreferredCommunicationChannelCreateUpdateHeading"
          >
            <Translate contentKey="valiusaioApp.fieldPreferredCommunicationChannel.home.createOrEditLabel">
              Create or edit a FieldPreferredCommunicationChannel
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
                  id="field-preferred-communication-channel-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('valiusaioApp.fieldPreferredCommunicationChannel.preferredCommunicationChannel')}
                id="field-preferred-communication-channel-preferredCommunicationChannel"
                name="preferredCommunicationChannel"
                data-cy="preferredCommunicationChannel"
                type="text"
              />
              <Button
                tag={Link}
                id="cancel-save"
                data-cy="entityCreateCancelButton"
                to="/field-preferred-communication-channel"
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

export default FieldPreferredCommunicationChannelUpdate;
