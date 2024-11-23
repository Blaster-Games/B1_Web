import React from 'react';
import 'tailwindcss/tailwind.css';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';

const GamePlatformMainPage = () => {
  return (
    <div className="bg-gray-900 text-white min-h-screen">
      {/* 헤더 영역 */}
      <header className="bg-gray-700 py-4 px-6">
        <h1 className="text-3xl font-bold">게임 플랫폼</h1>
      </header>

      {/* 캐루셀 영역 */}
      <div className="p-6">
        <Carousel autoPlay infiniteLoop showThumbs={false} showStatus={false} className="rounded-lg overflow-hidden">
          <div>
            <img src="https://via.placeholder.com/1200x600" alt="Banner 1" />
            <p className="legend">최신 게임 출시!</p>
          </div>
          <div>
            <img src="https://via.placeholder.com/1200x600" alt="Banner 2" />
            <p className="legend">가장 인기 있는 게임들!</p>
          </div>
          <div>
            <img src="https://via.placeholder.com/1200x600" alt="Banner 3" />
            <p className="legend">지금 바로 시작하세요!</p>
          </div>
        </Carousel>
      </div>

      {/* 게임 배너 섹션 */}
      <div className="p-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {['게임 1', '게임 2', '게임 3', '게임 4', '게임 5', '게임 6'].map((game, index) => (
          <div
            key={index}
            className="bg-gray-800 rounded-lg p-4 hover:bg-gray-700 transition duration-300 cursor-pointer"
          >
            <img
              src={`https://via.placeholder.com/400x200?text=${game}`}
              alt={`${game} 배너`}
              className="w-full h-48 object-cover rounded-md mb-4"
            />
            <h2 className="text-xl font-semibold">{game}</h2>
            <p className="mt-2">이 게임을 플레이하고 최고의 경험을 즐겨보세요!</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default GamePlatformMainPage;
