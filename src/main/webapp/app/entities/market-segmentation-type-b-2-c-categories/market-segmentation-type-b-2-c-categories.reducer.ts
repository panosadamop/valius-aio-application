import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';
import { loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IMarketSegmentationTypeB2cCategories, defaultValue } from 'app/shared/model/market-segmentation-type-b-2-c-categories.model';

const initialState: EntityState<IMarketSegmentationTypeB2cCategories> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/market-segmentation-type-b-2-c-categories';

// Actions

export const getEntities = createAsyncThunk(
  'marketSegmentationTypeB2cCategories/fetch_entity_list',
  async ({ page, size, sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
    return axios.get<IMarketSegmentationTypeB2cCategories[]>(requestUrl);
  }
);

export const getEntity = createAsyncThunk(
  'marketSegmentationTypeB2cCategories/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IMarketSegmentationTypeB2cCategories>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'marketSegmentationTypeB2cCategories/create_entity',
  async (entity: IMarketSegmentationTypeB2cCategories, thunkAPI) => {
    return axios.post<IMarketSegmentationTypeB2cCategories>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'marketSegmentationTypeB2cCategories/update_entity',
  async (entity: IMarketSegmentationTypeB2cCategories, thunkAPI) => {
    return axios.put<IMarketSegmentationTypeB2cCategories>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'marketSegmentationTypeB2cCategories/partial_update_entity',
  async (entity: IMarketSegmentationTypeB2cCategories, thunkAPI) => {
    return axios.patch<IMarketSegmentationTypeB2cCategories>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'marketSegmentationTypeB2cCategories/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    return await axios.delete<IMarketSegmentationTypeB2cCategories>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

// slice

export const MarketSegmentationTypeB2cCategoriesSlice = createEntitySlice({
  name: 'marketSegmentationTypeB2cCategories',
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

export const { reset } = MarketSegmentationTypeB2cCategoriesSlice.actions;

// Reducer
export default MarketSegmentationTypeB2cCategoriesSlice.reducer;
