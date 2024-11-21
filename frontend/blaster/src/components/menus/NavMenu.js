import React from 'react';
import { Link } from 'react-router-dom';

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
          <li>
            <Link
              to="/$1"
              className="text-white hover:text-blue-400 transition duration-300"
            >
              게임 1
            </Link>
          </li>
          <li>
            <Link
              to="/$1"
              className="text-white hover:text-blue-400 transition duration-300"
            >
              게임 2
            </Link>
          </li>
          <li>
            <Link
              to="/$1"
              className="text-white hover:text-blue-400 transition duration-300"
            >
              게임 3
            </Link>
          </li>
        </ul>
      </nav>
    </header>
  );
}

export default NavMenu;
