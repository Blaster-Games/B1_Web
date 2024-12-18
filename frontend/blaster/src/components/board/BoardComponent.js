import React, { useEffect, useRef } from 'react';
import PostListItem from './PostListItem';
import { SORT } from '../../constants/boardConstants';
import useCustomMove from '../../hooks/useCustomMove';
import useCustomLogin from '../../hooks/useCustomLogin';
import { postSearchGet } from '../../api/boardApi';

function BoardComponent({ name, pageInfo, setPageInfo }) {
  const sortRef = useRef(null);
  const searchRef = useRef(null);
  const { page, size, sort, category, setQueryParams, moveToCreate } =
    useCustomMove();
  const { isLogin, loginState, redirectToLogin } = useCustomLogin();

  useEffect(() => {
    sortRef.current.value = sort;
  });

  const handleClickCreatePost = () => {
    if (!isLogin) {
      redirectToLogin();
      return;
    }
    moveToCreate();
  };

  function handleChangeSort() {
    setQueryParams(() => ({ page, size, sort: sortRef.current.value }));
  }

  function handleClickSearch() {
    postSearchGet({
      keyword: searchRef.current.value,
      sort: sortRef.current.value,
    })
      .then((res) => {
        setPageInfo(res);
      })
      .catch(console.error);
  }

  return (
    <div className="flex-1 flex flex-col bg-gray-800 text-gray-100 rounded-t-lg shadow-lg p-8 h-full max-h-screen min-h-[70vh] pt-12">
      {/* 공지 게시판 제목 */}
      <div className="flex justify-between items-center mb-8">
        <h1 className="text-3xl font-bold">{name}</h1>
        {/* 검색창 */}
        <div className="flex items-center justify-center flex-1 relative">
          <input
            ref={searchRef}
            type="text"
            placeholder="검색..."
            className="bg-gray-700 text-gray-100 p-2 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 w-[80%] pr-10"
          />
          <button
            onClick={handleClickSearch}
            className="absolute inset-y-0 right-[10%] flex items-center pr-3 focus:outline-none">
            <svg
              className="w-5 h-5 text-gray-400"
              fill="none"
              stroke="currentColor"
              viewBox="0 0 24 24"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                strokeLinecap="round"
                strokeLinejoin="round"
                strokeWidth="2"
                d="M21 21l-4.35-4.35m1.67-5.15a7.5 7.5 0 11-15 0 7.5 7.5 0 0115 0z"
              ></path>
            </svg>
          </button>
        </div>
        {/* 조회 순 드롭다운 */}
        <select
          onChange={handleChangeSort}
          ref={sortRef}
          className="bg-indigo-900 text-white px-4 py-2 rounded-lg hover:bg-indigo-700 ml-4"
        >
          <option value={`${SORT.CREATED_AT}`}>최신 순</option>
          <option value={`${SORT.VIEW_COUNT}`}>조회 순</option>
          <option value={`${SORT.LIKE_COUNT}`}>추천 순</option>
        </select>
        {/* 글 작성 버튼 */}
        {category === 'notice' && loginState.userRole === 'USER' ? (
          <></>
        ) : (
          <div
            onClick={handleClickCreatePost}
            className="bg-emerald-600 text-white px-4 py-2 rounded-lg hover:bg-green-600 ml-4"
          >
            글 작성
          </div>
        )}
      </div>
      <div className="overflow-y-auto scrollbar">
        {/* 게시글 목록 */}
        {pageInfo.itemList && Array.isArray(pageInfo.itemList) ? (
          pageInfo.itemList.map((item) => (
            <PostListItem
              key={item.id}
              id={item.id}
              title={item.title}
              content={item.content}
              author={item.memberName}
              createdAt={item.createdAt}
              likes={item.likeCount}
              commentCount={item.commentCount}
              viewCount={item.viewCount}
            />
          ))
        ) : (
          <></>
        )}
      </div>
    </div>
  );
}

export default BoardComponent;
