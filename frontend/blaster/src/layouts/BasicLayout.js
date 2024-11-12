import React from 'react';
import NavMenu from '../components/menus/NavMenu';
import SideMenu from '../components/menus/SideMenu';

function BasicLayout(x) {
  return (
    <div className="flex flex-col min-h-screen gap-4 p-4 bg-gray-900">
      <NavMenu />
      <div className="flex h-full gap-4">
        <SideMenu />
        {x.children}
      </div>
    </div>
  );
}

export default BasicLayout;
