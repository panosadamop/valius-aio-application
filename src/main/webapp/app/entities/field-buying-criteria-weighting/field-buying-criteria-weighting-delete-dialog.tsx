import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './field-buying-criteria-weighting.reducer';

export const FieldBuyingCriteriaWeightingDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const fieldBuyingCriteriaWeightingEntity = useAppSelector(state => state.fieldBuyingCriteriaWeighting.entity);
  const updateSuccess = useAppSelector(state => state.fieldBuyingCriteriaWeighting.updateSuccess);

  const handleClose = () => {
    navigate('/field-buying-criteria-weighting');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(fieldBuyingCriteriaWeightingEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="fieldBuyingCriteriaWeightingDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="valiusaioApp.fieldBuyingCriteriaWeighting.delete.question">
        <Translate
          contentKey="valiusaioApp.fieldBuyingCriteriaWeighting.delete.question"
          interpolate={{ id: fieldBuyingCriteriaWeightingEntity.id }}
        >
          Are you sure you want to delete this FieldBuyingCriteriaWeighting?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-fieldBuyingCriteriaWeighting"
          data-cy="entityConfirmDeleteButton"
          color="danger"
          onClick={confirmDelete}
        >
          <FontAwesomeIcon icon="trash" />
          &nbsp;
          <Translate contentKey="entity.action.delete">Delete</Translate>
        </Button>
      </ModalFooter>
    </Modal>
  );
};

export default FieldBuyingCriteriaWeightingDeleteDialog;
