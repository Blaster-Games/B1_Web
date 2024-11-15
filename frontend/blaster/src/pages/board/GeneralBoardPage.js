import React, { useEffect, useState } from 'react';
import BoardComponent from '../../components/board/BoardComponent';
import Pagination from '../../components/board/Pagination';
import BasicLayout from '../../layouts/BasicLayout';
import { CATEGORY, GAME, SORT } from '../../constants/boardConstants';
import { postListGet } from '../../api/boardApi';

const initialRequestParam = {
  page: 1,
  size: 10,
  game: GAME.BLASTER,
  category: CATEGORY.GENERAL,
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

function GeneralBoardPage() {
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
            name={'자유 게시판'}
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

export default GeneralBoardPage;
