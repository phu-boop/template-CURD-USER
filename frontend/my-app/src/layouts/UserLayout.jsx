import {Outlet, Link} from "react-router-dom";
import Button from "../components/Button.jsx";
import {useAuthContext} from "../features/auth/AuthProvider.jsx";
import {logout as ApiLogout} from "../services/auth/authService.js"
const UserLayout = () => {
    const {logout} = useAuthContext();
    const handelOnclickLogout = async () => {
        const respond = await ApiLogout();
        console.log(respond);
        if (respond) {
            logout();
        }
    }
    return (
        <div className="flex flex-col min-h-screen">
            {/* Header */}
            <header className="bg-white shadow-md h-16 flex items-center justify-between px-6">
                <Link to="/" className="text-xl font-bold text-blue-600">
                    MyWebsite
                </Link>
                <nav className="space-x-6">
                    <Link to="/" className="hover:text-blue-500">Home</Link>
                    <Link to="/login" className="hover:text-blue-500">Login</Link>
                    <Link to="/register" className="hover:text-blue-500">Register</Link>
                    <Link to="/profile" className="hover:text-blue-500">Profile</Link>
                    <Button onClick={handelOnclickLogout} className="hover:text-blue-500">Logout</Button>
                </nav>
            </header>

            {/* Main Content */}
            <main className="flex-1 p-6 bg-gray-50">
                <Outlet/>
            </main>

            {/* Footer */}
            <footer className="bg-white border-t text-center py-4">
                <p className="text-gray-500 text-sm">
                    © {new Date().getFullYear()} MyWebsite. All rights reserved.
                </p>
            </footer>
        </div>
    );
};

export default UserLayout;
