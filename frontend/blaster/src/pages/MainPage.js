import React from 'react';
import 'tailwindcss/tailwind.css';
import { Carousel } from 'react-responsive-carousel';
import 'react-responsive-carousel/lib/styles/carousel.min.css';
import { Link } from 'react-router-dom';
import { gameInfo } from '../gameInfo';

const GamePlatformMainPage = () => {
  return (
    <div className="bg-gray-900 text-white min-h-screen">
      <div className="flex items-center justify-between px-12 bg-gray-700 py-4">
        {/* 헤더 영역 */}
        <header className="flex items-center">
          <h1 className="text-3xl font-bold">Indie Game Connect</h1>
        </header>
        <div>
          <ul className="flex space-x-6">
            {gameInfo.map((game) => (
              // eslint-disable-next-line react/jsx-key
              <li>
                <Link
                  to={game.path ? game.path : `/${game.pathVariable}`}
                  className="text-white hover:text-blue-400 transition duration-300"
                >
                  {game.name}
                </Link>
              </li>
            ))}
          </ul>
        </div>
      </div>

      {/* 캐루셀 영역 */}
      <div className="p-6">
        <Carousel
          autoPlay
          infiniteLoop
          showThumbs={false}
          showStatus={false}
          className="rounded-lg overflow-hidden"
        >
          <div className="flex flex-col justify-center">
            <img
              src="https://via.placeholder.com/1200x600"
              alt="Banner 1"
              className="rounded-lg h-96 object-cover mx-auto"
            />
            <p className="legend mt-2">최신 게임 출시!</p>
          </div>
          <div className="flex flex-col justify-center">
            <img
              src="https://t1.kakaocdn.net/gamepub/pub-img/poe/main/main_promotion_banner/202411/1732226458833_57.jpg"
              alt="Banner 2"
              className="rounded-lg h-96 object-cover mx-auto"
            />
            <p className="legend mt-2">가장 인기 있는 게임들!</p>
          </div>
          <div className="flex flex-col justify-center">
            <img
              src="https://t1.daumcdn.net/gamewiki/poe/1.%20%EA%B3%B5%ED%99%88TopBanner_PC_920x270v2.jpg"
              alt="Banner 3"
              className="rounded-lg h-96 object-cover mx-auto"
            />
            <p className="legend mt-2">지금 바로 시작하세요!</p>
          </div>
        </Carousel>
      </div>

      {/* 게임 배너 섹션 */}
      <div className="p-6 grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        {gameInfo.map((game, index) => (
          <Link
            to={game.path ? game.path : `/${game.pathVariable}`}
            key={index}
            className="bg-gray-800 rounded-lg p-4 hover:bg-gray-700 transition duration-300 cursor-pointer"
          >
            <img
              src={
                game.imagePath
                  ? game.imagePath
                  : `https://via.placeholder.com/400x200?text=${game.name}`
              }
              alt={`${game.name} 배너`}
              className="w-full h-48 object-cover rounded-md mb-4"
            />
            <h2 className="text-xl font-semibold">{game.name}</h2>
            <p className="mt-2">{game.description}</p>
          </Link>
        ))}
      </div>
    </div>
  );
};

export default GamePlatformMainPage;
