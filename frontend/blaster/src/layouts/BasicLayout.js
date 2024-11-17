import React from 'react';
import NavMenu from '../components/menus/NavMenu';
import SideMenu from '../components/menus/SideMenu';

function BasicLayout(x) {
  return (
    <div className="flex flex-col min-h-screen gap-4 p-4 bg-gray-900">
      <NavMenu />
      <div className="flex flex-grow gap-4">
        <SideMenu />
        <div className="flex-1 flex flex-col">
          {x.children}
        </div>
      </div>
    </div>
  );
}

export default BasicLayout;
