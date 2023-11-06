import React, { useEffect, useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import { Modal, ModalHeader, ModalBody, ModalFooter, Button } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';
import { getEntity, deleteEntity } from './market-segmentation-type-b-2-b-categories.reducer';

export const MarketSegmentationTypeB2bCategoriesDeleteDialog = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();
  const { id } = useParams<'id'>();

  const [loadModal, setLoadModal] = useState(false);

  useEffect(() => {
    dispatch(getEntity(id));
    setLoadModal(true);
  }, []);

  const marketSegmentationTypeB2bCategoriesEntity = useAppSelector(state => state.marketSegmentationTypeB2bCategories.entity);
  const updateSuccess = useAppSelector(state => state.marketSegmentationTypeB2bCategories.updateSuccess);

  const handleClose = () => {
    navigate('/market-segmentation-type-b-2-b-categories');
  };

  useEffect(() => {
    if (updateSuccess && loadModal) {
      handleClose();
      setLoadModal(false);
    }
  }, [updateSuccess]);

  const confirmDelete = () => {
    dispatch(deleteEntity(marketSegmentationTypeB2bCategoriesEntity.id));
  };

  return (
    <Modal isOpen toggle={handleClose}>
      <ModalHeader toggle={handleClose} data-cy="marketSegmentationTypeB2bCategoriesDeleteDialogHeading">
        <Translate contentKey="entity.delete.title">Confirm delete operation</Translate>
      </ModalHeader>
      <ModalBody id="valiusaioApp.marketSegmentationTypeB2bCategories.delete.question">
        <Translate
          contentKey="valiusaioApp.marketSegmentationTypeB2bCategories.delete.question"
          interpolate={{ id: marketSegmentationTypeB2bCategoriesEntity.id }}
        >
          Are you sure you want to delete this MarketSegmentationTypeB2bCategories?
        </Translate>
      </ModalBody>
      <ModalFooter>
        <Button color="secondary" onClick={handleClose}>
          <FontAwesomeIcon icon="ban" />
          &nbsp;
          <Translate contentKey="entity.action.cancel">Cancel</Translate>
        </Button>
        <Button
          id="jhi-confirm-delete-marketSegmentationTypeB2bCategories"
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

export default MarketSegmentationTypeB2bCategoriesDeleteDialog;
