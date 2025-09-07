import {useState} from "react";
import Button from "../components/Button.jsx";
import Input from "../components/Input.jsx";
import {loginUser} from "../services/auth/authService.js";
import Alert from "../components/Alert.jsx";
import Swal from "sweetalert2";
import {useNavigate} from "react-router-dom";
import {useAuthContext} from "../features/auth/AuthProvider";
import {FcGoogle} from "react-icons/fc";
import {FaFacebook, FaApple} from "react-icons/fa";

export default function Login() {
    const navigate = useNavigate();
    const {login} = useAuthContext();
    const [form, setForm] = useState({email: "", password: ""});
    const [error, setError] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        setError(null);

        try {
            const response = await loginUser(form);

            if (response.code === "1000") {
                const userData = response.data.userRespond;
                const token = response.data.token;

                const rolesArray = userData.roles.map((role) => role.name);

                login(
                    token,
                    rolesArray,
                    userData.id,
                    userData.email,
                    userData.name,
                    userData.fullName,
                    userData
                );

                Swal.fire({
                    title: "Chúc mừng!",
                    text: "Bạn đã đăng nhập thành công!",
                    icon: "success",
                    confirmButtonText: "OK",
                }).then((result) => {
                    if (result.isConfirmed) {
                        if (rolesArray.includes("ADMIN")) {
                            navigate("/admin");
                        } else {
                            navigate("/");
                        }
                    }
                });
            } else {
                setError(response.message || "Đăng nhập thất bại");
            }
        } catch (err) {
            console.error(err);
            setError(err.response.data.message || "Đăng nhập thất bại");
        } finally {
            setLoading(false);
        }
    };

// Hàm xử lý đăng nhập bằng mạng xã hội
    const handleSocialLogin = (provider) => {
        // Tạo URL redirect sau khi đăng nhập thành công (tuỳ thuộc vào role)
        const redirectUrl = window.location.origin + (
            localStorage.getItem('userRole') === 'ADMIN' ? '/admin' : '/'
        );

        // Mã hoá redirect URL để truyền như tham số
        const encodedRedirectUrl = encodeURIComponent(redirectUrl);

        // Tuỳ thuộc vào provider mà có endpoint khác nhau
        let authUrl = '';

        switch (provider) {
            case 'google':
                authUrl = `http://localhost:8080/oauth2/authorization/google?redirect_uri=${encodedRedirectUrl}`;
                break;
            case 'facebook':
                authUrl = `http://api.yourdomain.com/auth/facebook?redirect_uri=${encodedRedirectUrl}`;
                break;
            case 'apple':
                authUrl = `http://api.yourdomain.com/auth/apple?redirect_uri=${encodedRedirectUrl}`;
                break;
            default:
                console.error('Provider không được hỗ trợ');
                return;
        }

        window.location.href = authUrl;
    };


    return (
        <div className="flex items-center justify-center min-h-screen bg-slate-100">
            <form
                onSubmit={handleSubmit}
                className="bg-white p-6 rounded-xl shadow-lg w-full max-w-sm space-y-4"
            >
                <h2 className="text-2xl font-bold text-center text-sky-600">Đăng nhập</h2>

                <Input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={form.email}
                    onChange={handleChange}
                    required
                />
                <Input
                    type="password"
                    name="password"
                    placeholder="Mật khẩu"
                    value={form.password}
                    onChange={handleChange}
                    required
                />

                {error && (
                    <Alert type="error" message={error} onClose={() => setError(null)}/>
                )}

                <Button
                    type="submit"
                    variant="primary"
                    size="sm"
                    disabled={loading}
                    className="w-full"
                >
                    {loading ? "Đang đăng nhập..." : "Đăng nhập"}
                </Button>

                {/* Phần đăng nhập bằng mạng xã hội */}
                <div className="relative my-6">
                    <div className="absolute inset-0 flex items-center">
                        <div className="w-full border-t border-gray-300"></div>
                    </div>
                    <div className="relative flex justify-center text-sm">
                        <span className="px-2 bg-white text-gray-500">Hoặc tiếp tục với</span>
                    </div>
                </div>

                <div className="grid grid-cols-3 gap-3">
                    {/* Nút Google */}
                    <button
                        type="button"
                        onClick={() => handleSocialLogin('google')}
                        className="w-full inline-flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-sky-500 transition-colors"
                    >
                        <FcGoogle className="h-5 w-5"/>
                    </button>

                    {/* Nút Facebook */}
                    <button
                        type="button"
                        onClick={() => handleSocialLogin('facebook')}
                        className="w-full inline-flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-sky-500 transition-colors"
                    >
                        <FaFacebook className="h-5 w-5 text-blue-600"/>
                    </button>

                    {/* Nút Apple */}
                    <button
                        type="button"
                        onClick={() => handleSocialLogin('apple')}
                        className="w-full inline-flex justify-center py-2 px-4 border border-gray-300 rounded-md shadow-sm bg-white text-sm font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-sky-500 transition-colors"
                    >
                        <FaApple className="h-5 w-5 text-gray-900"/>
                    </button>
                </div>

                <p className="text-sm text-center text-slate-500">
                    Chưa có tài khoản?{" "}
                    <a href="/register" className="text-sky-600 hover:underline">
                        Đăng ký
                    </a>
                </p>
            </form>
        </div>
    );
}