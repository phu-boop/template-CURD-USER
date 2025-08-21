import {useEffect, useState} from "react";
import {Navigate, Outlet} from "react-router-dom";
import {useAuthContext} from "../features/auth/AuthProvider.jsx";
import Swal from "sweetalert2";

export default function ProtectedRoute({allowedRoles}) {
    const {role} = useAuthContext();
    const [alertShown, setAlertShown] = useState(false);
    const [redirect, setRedirect] = useState(null);

    useEffect(() => {
        if (!role) {
            Swal.fire({
                title: "Chưa đăng nhập",
                text: "Vui lòng đăng nhập để tiếp tục!",
                icon: "warning",
                confirmButtonText: "OK"
            }).then(() => {
                setRedirect("/login");
            });
            setAlertShown(true);
        } else if (!allowedRoles.includes(role)) {
            Swal.fire({
                title: "Không có quyền",
                text: "Bạn không có quyền truy cập trang này!",
                icon: "error",
                confirmButtonText: "OK"
            }).then(() => {
                setRedirect("/");
            });
            setAlertShown(true);
        }
    }, [role, allowedRoles]);

    if (redirect) return <Navigate to={redirect} replace/>;
    if (alertShown) return null;

    return <Outlet/>;
}
