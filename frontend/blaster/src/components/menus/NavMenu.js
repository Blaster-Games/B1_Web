import React from 'react';
import { Link } from 'react-router-dom';
import { gameInfo } from '../../gameInfo';

function NavMenu() {
  return (
    <header className="bg-gray-700 p-4 flex justify-between items-center shadow-md rounded-lg mb-6">
      <nav>
        <ul className="flex items-center space-x-6">
          <li className={'ml-4'}>
            <Link
              to="/"
              className="text-white hover:text-blue-400 text-lg transition duration-300"
            >
              <h1 className="text-3xl font-bold">
                <span className={'text-indigo-100'}>Indie</span>
                <span className={'text-orange-300'}>Connect</span>
              </h1>
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
