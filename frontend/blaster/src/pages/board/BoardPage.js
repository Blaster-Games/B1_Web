import React from 'react';
import { GAME, SORT } from '../../constants/boardConstants';
import useCustomBoard from '../../hooks/useCustomBoard';
import BasicLayout from '../../layouts/BasicLayout';
import BoardComponent from '../../components/board/BoardComponent';
import Pagination from '../../components/board/Pagination';

const initialRequestParam = {
  page: 1,
  size: 10,
  game: GAME.BLASTER,
  category: '',
  sort: SORT.CREATED_AT,
};

function BoardPage() {
  const { pageInfo, category } = useCustomBoard(initialRequestParam);

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
