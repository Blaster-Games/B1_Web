import React from 'react';
import useCustomLogin from '../hooks/useCustomLogin';
import { gameInfo } from '../gameInfo';

function GameMainComponent() {
  const { game } = useCustomLogin();
  const info = gameInfo.find((g) => g.pathVariable === game);
  return (
    <div className="flex-1 flex flex-col bg-gray-800 text-gray-100 rounded-lg shadow-lg p-4">
      <main className="p-8 flex flex-col items-center bg-gray-700 rounded-lg shadow-lg">
        <h2 className="text-4xl font-bold mb-6 text-white">
          {info.name}에 오신 것을 환영합니다!
        </h2>
        <button className="bg-blue-500 hover:bg-blue-400 text-white py-3 px-6 rounded-lg transition duration-300 shadow-md">
          게임 다운로드
        </button>
      </main>

      <section className="flex justify-around p-8 mt-8 rounded h-full bg-gray-700">
        {info.images ? (
          info.images.map((path) => (
            <div>
              <img className="rounded" src={`${path}`} alt="" />
            </div>
          ))
        ) : (
          <></>
        )}
        {/*<div className={''}>*/}
        {/*  <img className="rounded" src="/blaster1.png" alt="" />*/}
        {/*</div>*/}
        {/*<div className={''}>*/}
        {/*  <img className="rounded" src="/blaster2.png" alt="" />*/}
        {/*</div>*/}
      </section>
    </div>
  );
}

export default GameMainComponent;
