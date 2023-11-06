import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';
import { loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IMarketSegmentationTypeB2bCategories, defaultValue } from 'app/shared/model/market-segmentation-type-b-2-b-categories.model';

const initialState: EntityState<IMarketSegmentationTypeB2bCategories> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/market-segmentation-type-b-2-b-categories';

// Actions

export const getEntities = createAsyncThunk(
  'marketSegmentationTypeB2bCategories/fetch_entity_list',
  async ({ page, size, sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
    return axios.get<IMarketSegmentationTypeB2bCategories[]>(requestUrl);
  }
);

export const getEntity = createAsyncThunk(
  'marketSegmentationTypeB2bCategories/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IMarketSegmentationTypeB2bCategories>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'marketSegmentationTypeB2bCategories/create_entity',
  async (entity: IMarketSegmentationTypeB2bCategories, thunkAPI) => {
    return axios.post<IMarketSegmentationTypeB2bCategories>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'marketSegmentationTypeB2bCategories/update_entity',
  async (entity: IMarketSegmentationTypeB2bCategories, thunkAPI) => {
    return axios.put<IMarketSegmentationTypeB2bCategories>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'marketSegmentationTypeB2bCategories/partial_update_entity',
  async (entity: IMarketSegmentationTypeB2bCategories, thunkAPI) => {
    return axios.patch<IMarketSegmentationTypeB2bCategories>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'marketSegmentationTypeB2bCategories/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    return await axios.delete<IMarketSegmentationTypeB2bCategories>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

// slice

export const MarketSegmentationTypeB2bCategoriesSlice = createEntitySlice({
  name: 'marketSegmentationTypeB2bCategories',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;
        const links = parseHeaderForLinks(headers.link);

        return {
          ...state,
          loading: false,
          links,
          entities: loadMoreDataWhenScrolled(state.entities, data, links),
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = MarketSegmentationTypeB2bCategoriesSlice.actions;

// Reducer
export default MarketSegmentationTypeB2bCategoriesSlice.reducer;
