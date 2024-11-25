import React, { useEffect, useState } from 'react';
import { GAME, SORT } from '../../constants/boardConstants';
import BasicLayout from '../../layouts/BasicLayout';
import BoardComponent from '../../components/board/BoardComponent';
import Pagination from '../../components/board/Pagination';
import useCustomMove from '../../hooks/useCustomMove';
import { postListGet } from '../../api/boardApi';

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

const initialRequestParam = {
  page: 1,
  size: 10,
  game: GAME.BLASTER,
  category: '',
  sort: SORT.CREATED_AT,
};

function BoardPage() {
  const { page, size, sort, category } = useCustomMove();
  const [pageInfo, setPageInfo] = useState(initialPageInfo);
  const [requestParam, setRequestParam] = useState(initialRequestParam);

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

  return (
    <BasicLayout>
      <div className="flex flex-col flex-1 overflow-hidden">
        <div className="flex-1 overflow-y-auto">
          <BoardComponent
            name={category === 'notice' ? '공지 게시판' : '자유 게시판'}
            pageInfo={pageInfo}
          />
        </div>
        <Pagination pageInfo={pageInfo} />
      </div>
    </BasicLayout>
  );
}

export default BoardPage;
