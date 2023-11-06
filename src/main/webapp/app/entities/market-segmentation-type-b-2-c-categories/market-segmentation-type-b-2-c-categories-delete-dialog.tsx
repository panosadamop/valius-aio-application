import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './market-segmentation-type-b-2-c-categories.reducer';

export const MarketSegmentationTypeB2cCategoriesDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const marketSegmentationTypeB2cCategoriesEntity = useAppSelector(state => state.marketSegmentationTypeB2cCategories.entity);
  const updateSuccess = useAppSelector(state => state.marketSegmentationTypeB2cCategories.updateSuccess);

  const handleClose = () => {
    navigate('/market-segmentation-type-b-2-c-categories');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(marketSegmentationTypeB2cCategoriesEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="marketSegmentationTypeB2cCategoriesDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="valiusaioApp.marketSegmentationTypeB2cCategories.delete.question">
        <Translate
          contentKey="valiusaioApp.marketSegmentationTypeB2cCategories.delete.question"
          interpolate={{ id: marketSegmentationTypeB2cCategoriesEntity.id }}
        >
          Are you sure you want to delete this MarketSegmentationTypeB2cCategories?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-marketSegmentationTypeB2cCategories"
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

export default MarketSegmentationTypeB2cCategoriesDeleteDialog;
