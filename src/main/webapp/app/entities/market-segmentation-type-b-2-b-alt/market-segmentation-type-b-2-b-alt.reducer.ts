import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending, isRejected } from '@reduxjs/toolkit';
import { loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IMarketSegmentationTypeB2bAlt, defaultValue } from 'app/shared/model/market-segmentation-type-b-2-b-alt.model';

const initialState: EntityState<IMarketSegmentationTypeB2bAlt> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/market-segmentation-type-b-2-b-alts';

// Actions

export const getEntities = createAsyncThunk(
  'marketSegmentationTypeB2bAlt/fetch_entity_list',
  async ({ page, size, sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}&` : '?'}cacheBuster=${new Date().getTime()}`;
    return axios.get<IMarketSegmentationTypeB2bAlt[]>(requestUrl);
  }
);

export const getEntity = createAsyncThunk(
  'marketSegmentationTypeB2bAlt/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IMarketSegmentationTypeB2bAlt>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

export const createEntity = createAsyncThunk(
  'marketSegmentationTypeB2bAlt/create_entity',
  async (entity: IMarketSegmentationTypeB2bAlt, thunkAPI) => {
    return axios.post<IMarketSegmentationTypeB2bAlt>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const updateEntity = createAsyncThunk(
  'marketSegmentationTypeB2bAlt/update_entity',
  async (entity: IMarketSegmentationTypeB2bAlt, thunkAPI) => {
    return axios.put<IMarketSegmentationTypeB2bAlt>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const partialUpdateEntity = createAsyncThunk(
  'marketSegmentationTypeB2bAlt/partial_update_entity',
  async (entity: IMarketSegmentationTypeB2bAlt, thunkAPI) => {
    return axios.patch<IMarketSegmentationTypeB2bAlt>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError }
);

export const deleteEntity = createAsyncThunk(
  'marketSegmentationTypeB2bAlt/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    return await axios.delete<IMarketSegmentationTypeB2bAlt>(requestUrl);
  },
  { serializeError: serializeAxiosError }
);

// slice

export const MarketSegmentationTypeB2bAltSlice = createEntitySlice({
  name: 'marketSegmentationTypeB2bAlt',
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

export const { reset } = MarketSegmentationTypeB2bAltSlice.actions;

// Reducer
export default MarketSegmentationTypeB2bAltSlice.reducer;
