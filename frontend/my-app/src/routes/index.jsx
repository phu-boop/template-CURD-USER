import {Routes, Route} from "react-router-dom";
import {AuthProvider} from "../features/auth/AuthProvider";

// layouts
import AdminLayout from "../layouts/AdminLayout";
import UserLayout from "../layouts/UserLayout";

// pages
import Home from "../pages/Home";
import Login from "../pages/Login.jsx";
import Register from "../pages/Register.jsx";
import Dashboard from "../features/dashboard/pages/Dashboard";
import NotFound from "../pages/NotFound";
import ProtectedRoute from "./ProtectedRoute";
import Test from "../pages/Test.jsx";

export default function AppRoutes() {
    return (
        <AuthProvider>
            <Routes>
                {/* Public */}
                <Route element={<UserLayout/>}>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/login" element={<Login/>}/>
                    <Route path="/register" element={<Register/>}/>
                    <Route path="/test" element={<Test/>}/>
                </Route>

                {/* User layout */}
                <Route element={<ProtectedRoute allowedRoles={["User"]}/>}>
                    <Route path="/user" element={<UserLayout/>}>
                    </Route>
                </Route>

                {/* Admin layout */}
                <Route element={<ProtectedRoute allowedRoles={["Admin"]}/>}>
                    <Route path="/admin" element={<AdminLayout/>}>
                        <Route index element={<Dashboard/>}/>
                    </Route>
                </Route>

                {/* Not found */}
                <Route path="*" element={<NotFound/>}/>
            </Routes>
        </AuthProvider>
    );
}
