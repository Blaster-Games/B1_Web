import React, { useEffect, useState } from 'react';
import BasicLayout from '../../layouts/BasicLayout';
import BoardComponent from '../../components/board/BoardComponent';
import { CATEGORY, GAME, SORT } from '../../constants/boardConstants';
import { postListGet } from '../../api/boardApi';
import Pagination from '../../components/board/Pagination';

const initialRequestParam = {
  page: 1,
  size: 10,
  game: GAME.BLASTER,
  category: CATEGORY.NOTICE,
  sort: SORT.CREATED_AT,
};

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

function NoticeBoardPage() {
  const [pageInfo, setPageInfo] = useState(initialPageInfo);
  const [requestParam, setRequestParam] = useState(initialRequestParam);

  useEffect(() => {
    postListGet(requestParam)
      .then((res) => {
        console.log(res);
        setPageInfo(res);
      })
      .catch(console.error);
  }, [requestParam]);

  return (
    <BasicLayout>
      <div className="flex flex-col flex-1 overflow-hidden">
        <div className="flex-1 overflow-y-auto">
          <BoardComponent
            name={'공지 게시판'}
            pageInfo={pageInfo}
            requestParam={requestParam}
            setRequestParam={setRequestParam}
          />
        </div>
        <Pagination
          requestParam={requestParam}
          setRequestParam={setRequestParam}
          pageInfo={pageInfo}
        />
      </div>
    </BasicLayout>
  );
}

export default NoticeBoardPage;
