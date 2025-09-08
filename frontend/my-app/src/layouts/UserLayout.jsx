import {Outlet, Link} from "react-router-dom";
import Button from "../components/Button.jsx";
import {useAuthContext} from "../features/auth/AuthProvider.jsx";
import {logout as ApiLogout} from "../services/auth/authService.js"
import Swal from "sweetalert2";
const UserLayout = () => {
    const {logout} = useAuthContext();
    const handelOnclickLogout = async () => {
        // Hiển thị hộp thoại xác nhận
        const result = await Swal.fire({
            title: 'Bạn có chắc chắn?',
            text: "Bạn sẽ đăng xuất khỏi hệ thống!",
            icon: 'warning',
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Đăng xuất',
            cancelButtonText: 'Hủy bỏ',
            reverseButtons: true,
            customClass: {
                confirmButton: 'mr-2',
                cancelButton: 'ml-2'
            }
        });

        // Nếu người dùng xác nhận đăng xuất
        if (result.isConfirmed) {
            try {
                const respond = await ApiLogout();

                if (respond) {
                    logout();

                    // Hiển thị thông báo thành công
                    await Swal.fire({
                        title: 'Đã đăng xuất!',
                        text: 'Bạn đã đăng xuất thành công.',
                        icon: 'success',
                        timer: 2000,
                        showConfirmButton: false
                    });
                }
            } catch (error) {
                console.error('Lỗi khi đăng xuất:', error);

                // Hiển thị thông báo lỗi
                await Swal.fire({
                    title: 'Lỗi!',
                    text: 'Đã xảy ra lỗi trong quá trình đăng xuất. Vui lòng thử lại.',
                    icon: 'error',
                    confirmButtonText: 'OK',
                    confirmButtonColor: '#3b82f6'
                });
            }
        }
    };
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
