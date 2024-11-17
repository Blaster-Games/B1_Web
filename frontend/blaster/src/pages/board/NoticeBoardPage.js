import React from 'react';
import BasicLayout from '../../layouts/BasicLayout';
import BoardComponent from '../../components/board/BoardComponent';
import { CATEGORY, GAME, SORT } from '../../constants/boardConstants';
import Pagination from '../../components/board/Pagination';
import useCustomBoard from '../../hooks/useCustomBoard';

const initialRequestParam = {
  page: 1,
  size: 10,
  game: GAME.BLASTER,
  category: CATEGORY.NOTICE,
  sort: SORT.CREATED_AT,
};

function NoticeBoardPage() {
  const { pageInfo, requestParam, setRequestParam } =
    useCustomBoard(initialRequestParam);

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
