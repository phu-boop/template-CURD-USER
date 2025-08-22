// src/layouts/AdminLayout.jsx
import {Outlet} from "react-router-dom";
import {useState} from "react";
import {
    FiMenu,
    FiX,
    FiHome,
    FiBox,
    FiUsers,
    FiSettings,
    FiBell,
    FiLogOut,
    FiMessageSquare,
    FiHelpCircle
} from "react-icons/fi";
import {useAuthContext} from "../features/auth/AuthProvider";

const AdminLayout = () => {
    const [isSidebarOpen, setIsSidebarOpen] = useState(true);
    const {logout, email} = useAuthContext();

    const menuItems = [
        {icon: FiHome, label: "Dashboard", path: "/admin"},
        {icon: FiBox, label: "Products", path: "/admin/products"},
        {icon: FiUsers, label: "Users", path: "/admin/users"},
        {icon: FiSettings, label: "Settings", path: "/admin/settings"},
    ];

    const handleLogout = () => {
        if (window.confirm("Bạn có chắc muốn đăng xuất?")) {
            logout();
            window.location.href = "/login";
        }
    };

    return (
        <div className="flex h-screen bg-gray-50">
            {/* Sidebar Overlay */}
            {isSidebarOpen && (
                <div
                    className="fixed inset-0 bg-black bg-opacity-50 z-20 lg:hidden"
                    onClick={() => setIsSidebarOpen(false)}
                />
            )}

            {/* Sidebar */}
            <aside
                className={`fixed lg:static top-0 left-0 h-full bg-white shadow-xl z-30 transition-all duration-300 ${
                    isSidebarOpen ? "w-64 translate-x-0" : "w-0 -translate-x-full"
                } lg:translate-x-0 lg:w-64`}
            >
                <div className="flex flex-col h-full">
                    {/* Logo */}
                    <div className="h-20 flex items-center justify-between px-6 border-b border-gray-200">
                        <h1 className="text-2xl font-bold text-blue-600">Sneat</h1>
                        <button
                            onClick={() => setIsSidebarOpen(false)}
                            className="lg:hidden p-2 hover:bg-gray-100 rounded-lg"
                        >
                            <FiX className="w-5 h-5 text-gray-600"/>
                        </button>
                    </div>

                    {/* Navigation */}
                    <nav className="flex-1 px-4 py-6">
                        <ul className="space-y-2">
                            {menuItems.map((item, index) => (
                                <li key={index}>
                                    <a
                                        href={item.path}
                                        className="flex items-center px-4 py-3 text-gray-700 hover:bg-blue-50 hover:text-blue-600 rounded-xl transition-all duration-200 group"
                                    >
                                        <item.icon className="w-5 h-5 mr-3 group-hover:text-blue-600"/>
                                        <span className="font-medium">{item.label}</span>
                                    </a>
                                </li>
                            ))}
                        </ul>
                    </nav>

                    {/* Logout Button */}
                    <div className="p-4 border-t border-gray-200">
                        <button
                            onClick={handleLogout}
                            className="flex items-center w-full px-4 py-3 text-red-600 hover:bg-red-50 rounded-xl transition-all duration-200"
                        >
                            <FiLogOut className="w-5 h-5 mr-3"/>
                            <span className="font-medium">Đăng xuất</span>
                        </button>
                    </div>
                </div>
            </aside>

            {/* Main Content */}
            <div className="flex-1 flex flex-col min-w-0">
                {/* Header */}
                <header
                    className="h-20 bg-white shadow-sm border-b border-gray-200 px-6 flex items-center justify-between">
                    <div className="flex items-center">
                        <button
                            onClick={() => setIsSidebarOpen(true)}
                            className="p-2 hover:bg-gray-100 rounded-lg lg:hidden"
                        >
                            <FiMenu className="w-5 h-5 text-gray-600"/>
                        </button>
                        <h2 className="ml-4 text-xl font-semibold text-gray-800">Admin Dashboard</h2>
                    </div>

                    <div className="flex items-center space-x-4">
                        <button className="p-2 hover:bg-gray-100 rounded-lg relative">
                            <FiBell className="w-5 h-5 text-gray-600"/>
                            <span className="absolute top-1 right-1 w-2 h-2 bg-red-500 rounded-full"></span>
                        </button>

                        <div className="flex items-center space-x-3">
                            <div className="w-10 h-10 bg-blue-100 rounded-full flex items-center justify-center">
                <span className="text-blue-600 font-semibold">
                  {email?.charAt(0).toUpperCase()}
                </span>
                            </div>
                            <div className="hidden sm:block">
                                <p className="text-sm font-medium text-gray-800">{email}</p>
                                <p className="text-xs text-gray-500">Administrator</p>
                            </div>
                        </div>
                    </div>
                </header>

                {/* Content Area với khoảng cách và bo tròn */}
                <main className="flex-1 overflow-y-auto p-6">
                    <div className="max-w-7xl mx-auto space-y-6">
                        {/* Content Container với bo tròn và shadow */}
                        <div className="bg-white rounded-2xl shadow-sm border border-gray-200 p-6">
                            <Outlet/>
                        </div>
                    </div>
                </main>

                {/* Footer */}
                <footer className="bg-white border-t border-gray-200 py-6 px-6">
                    <div className="max-w-7xl mx-auto">
                        <div className="flex flex-col md:flex-row justify-between items-center space-y-4 md:space-y-0">
                            {/* Left Section */}
                            <div className="flex items-center space-x-6">
                                <p className="text-sm text-gray-600">
                                    © 2024 Sneat Admin. All rights reserved.
                                </p>
                                <div className="flex items-center space-x-4">
                                    <a href="#"
                                       className="text-gray-500 hover:text-blue-600 transition-colors duration-200">
                                        <FiMessageSquare className="w-4 h-4"/>
                                    </a>
                                    <a href="#"
                                       className="text-gray-500 hover:text-blue-600 transition-colors duration-200">
                                        <FiHelpCircle className="w-4 h-4"/>
                                    </a>
                                </div>
                            </div>

                            {/* Right Section */}
                            <div className="flex items-center space-x-6">
                                <a href="#"
                                   className="text-sm text-gray-600 hover:text-blue-600 transition-colors duration-200">
                                    Privacy Policy
                                </a>
                                <a href="#"
                                   className="text-sm text-gray-600 hover:text-blue-600 transition-colors duration-200">
                                    Terms of Service
                                </a>
                                <a href="#"
                                   className="text-sm text-gray-600 hover:text-blue-600 transition-colors duration-200">
                                    Contact
                                </a>
                            </div>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
    );
};

export default AdminLayout;