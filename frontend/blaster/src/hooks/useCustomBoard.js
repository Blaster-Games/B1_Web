import useCustomMove from './useCustomMove';
import { useEffect, useState } from 'react';
import { postListGet } from '../api/boardApi';
import { useParams } from 'react-router-dom';

const initialPageInfo = {
  itemList: [],
  pageNumList: null,
  current: 1,
  size: 10,
  prevPage: 0,
  nextPage: 11,
  totalCount: 100,
  totalPages: 1,
  prev: false,
  next: true,
};

const useCustomBoard = (RequestParam) => {
  const { category } = useParams();
  const { page, size, sort } = useCustomMove();
  const [pageInfo, setPageInfo] = useState(initialPageInfo);
  const [requestParam, setRequestParam] = useState(RequestParam);

  useEffect(() => {
    setRequestParam({
      ...requestParam,
      page: page,
      size: size,
      category: category.toUpperCase(),
      sort: sort,
    });
  }, [page, size, category, sort]);

  useEffect(() => {
    if (requestParam.category) {
      postListGet(requestParam)
        .then((res) => {
          setPageInfo(res);
        })
        .catch(console.error);
    }
  }, [requestParam]);

  return { pageInfo, requestParam, setRequestParam, category };
};

export default useCustomBoard;
