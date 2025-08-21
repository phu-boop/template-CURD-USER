// src/layouts/AdminLayout.jsx
import { Outlet } from "react-router-dom";
import { useState } from "react";

const AdminLayout = () => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  return (
    <div className="flex h-screen bg-gray-100">
      {/* Sidebar */}
      <aside
        className={`${
          isSidebarOpen ? "w-64" : "w-20"
        } bg-white shadow-lg transition-all duration-300`}
      >
        <div className="h-16 flex items-center justify-center border-b">
          <h1 className="text-xl font-bold">Sneat</h1>
        </div>
        <nav className="p-4">
          <ul className="space-y-2">
            <li className="hover:bg-gray-200 p-2 rounded">Dashboard</li>
            <li className="hover:bg-gray-200 p-2 rounded">Products</li>
            <li className="hover:bg-gray-200 p-2 rounded">Users</li>
            <li className="hover:bg-gray-200 p-2 rounded">Settings</li>
          </ul>
        </nav>
      </aside>

      {/* Main Content */}
      <div className="flex-1 flex flex-col">
        {/* Navbar */}
        <header className="h-16 bg-white shadow flex items-center justify-between px-4">
          <button
            onClick={() => setIsSidebarOpen(!isSidebarOpen)}
            className="p-2 bg-gray-200 rounded"
          >
            {isSidebarOpen ? "<<" : ">>"}
          </button>
          <div className="flex items-center space-x-4">
            <span>🔔</span>
            <span>👤 Admin</span>
          </div>
        </header>

        {/* Content */}
        <main className="p-6 overflow-y-auto flex-1">
          <Outlet />
        </main>
      </div>
    </div>
  );
};

export default AdminLayout;
