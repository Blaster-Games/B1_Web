import React from 'react';
import { Link } from 'react-router-dom';
import { gameInfo } from '../../gameInfo';

function NavMenu() {
  return (
    <header className="bg-gray-700 p-4 flex justify-between items-center shadow-md rounded-lg mb-6">
      <nav>
        <ul className="flex space-x-6">
          <li>
            <Link
              to="/"
              className="text-white hover:text-blue-400 text-lg transition duration-300"
            >
              <img
                src="/1.svg"
                alt="Home Icon"
                style={{ width: '30px', height: '30px' }} // 원하는 크기로 조정
              />
            </Link>
          </li>
          {gameInfo.map((gameInfo) => (
            // eslint-disable-next-line react/jsx-key
            <li>
              <Link
                to={gameInfo.path ? gameInfo.path : `/${gameInfo.pathVariable}`}
                className="text-white hover:text-blue-400 transition duration-300"
              >
                {gameInfo.name}
              </Link>
            </li>
          ))}
        </ul>
      </nav>
    </header>
  );
}

export default NavMenu;
