import {
  createSearchParams,
  useNavigate,
  useParams,
  useSearchParams,
} from 'react-router-dom';
import { useState } from 'react';
import { SORT } from '../constants/boardConstants';

const getNum = (param, defaultValue) => {
  return !param ? defaultValue : parseInt(param);
};

const getString = (param, defaultValue) => {
  return !param ? defaultValue : param;
};

const useCustomMove = () => {
  const navigate = useNavigate();

  const [refresh, setRefresh] = useState(false);

  const [queryParams, setQueryParams] = useSearchParams();

  const { category } = useParams();

  const page = getNum(queryParams.get('page'), 1);
  const size = getNum(queryParams.get('size'), 10);
  const sort = getString(queryParams.get('sort'), SORT.CREATED_AT);

  const queryDefault = createSearchParams({ page, size, sort }).toString();

  const moveToDetail = (id) => {
    navigate({
      pathname: `./${id}`,
      search: queryDefault,
    });
  };

  const moveToCreate = () => {
    navigate({
      pathname: `./create`,
      search: queryDefault,
    });
  };

  const moveToList = (pageParam) => {
    let queryStr = queryDefault;

    if (pageParam) {
      const pageNum = getNum(pageParam.page, 1);
      const sizeNum = getNum(pageParam.size, 10);
      const sort = getString(pageParam.sort, SORT.CREATED_AT);

      queryStr = createSearchParams({
        page: pageNum,
        size: sizeNum,
        sort: sort,
      }).toString();
    }

    setRefresh(!refresh);

    navigate({ pathname: `../${category}`, search: queryStr });
  };

  return {
    moveToList,
    moveToDetail,
    page,
    size,
    sort,
    queryParams,
    setQueryParams,
    refresh,
    category,
    moveToCreate,
  };
};

export default useCustomMove;
