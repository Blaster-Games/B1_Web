import React from 'react';
import BoardComponent from '../../components/board/BoardComponent';
import Pagination from '../../components/board/Pagination';
import BasicLayout from '../../layouts/BasicLayout';
import { GAME, SORT } from '../../constants/boardConstants';
import useCustomBoard from '../../hooks/useCustomBoard';

const initialRequestParam = {
  page: 1,
  size: 10,
  game: GAME.BLASTER,
  category: '',
  sort: SORT.CREATED_AT,
};

function GeneralBoardPage() {
  const { pageInfo, category } = useCustomBoard(initialRequestParam);
  return (
    <BasicLayout>
      <div className="flex flex-col flex-1 overflow-hidden">
        <div className="flex-1 overflow-y-auto">
          <BoardComponent
            name={category === 'notice' ? '공지 게시판' : '자유 게시'}
            pageInfo={pageInfo}
          />
        </div>
        <Pagination pageInfo={pageInfo} />
      </div>
    </BasicLayout>
  );
}

export default GeneralBoardPage;
