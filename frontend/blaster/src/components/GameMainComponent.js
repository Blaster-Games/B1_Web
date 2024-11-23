import React from 'react';

function GameMainComponent() {
  return (
    <div className="flex-1 flex flex-col bg-gray-800 text-gray-100 rounded-lg shadow-lg p-4">
      <main className="p-8 flex flex-col items-center bg-gray-700 rounded-lg shadow-lg">
        <h2 className="text-4xl font-bold mb-6 text-white">
          우리 게임에 오신 것을 환영합니다!
        </h2>
        <p className="text-lg text-center mb-8 text-gray-300">
          실시간으로 제공되는 맵 별 무기 통계와 커뮤니티 기능을 통해 더욱 풍성한
          게임 경험을 즐겨보세요.
        </p>
        <button className="bg-blue-500 hover:bg-blue-400 text-white py-3 px-6 rounded-lg transition duration-300 shadow-md">
          게임 다운로드
        </button>
      </main>

      <section className="p-8">
        <h3 className="text-3xl font-bold mb-4">주요 통계</h3>
        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          {/* 통계 카드 */}
          <div className="bg-gray-700 p-6 rounded-lg shadow-md transform hover:scale-105 transition duration-300">
            <h4 className="text-lg font-semibold mb-3">맵 별 무기 사용률</h4>
            {/* 통계 이미지나 차트 삽입 */}
            <div className="h-32 bg-gray-600 rounded"></div>
          </div>
          <div className="bg-gray-700 p-6 rounded-lg shadow-md transform hover:scale-105 transition duration-300">
            <h4 className="text-lg font-semibold mb-3">유저 인기 무기</h4>
            <div className="h-32 bg-gray-600 rounded"></div>
          </div>
        </div>
      </section>
    </div>
  );
}

export default GameMainComponent;
