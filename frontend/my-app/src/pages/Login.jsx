import {useState} from "react";
import Button from "../components/Button.jsx";
import Input from "../components/Input.jsx";
import {loginUser} from "../services/auth/authService.js";
import Alert from "../components/Alert.jsx";
import Swal from "sweetalert2";
import {useNavigate} from "react-router-dom";
import {useAuthContext} from "../features/auth/AuthProvider";

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
            setError(err.message || "Đăng nhập thất bại");
        } finally {
            setLoading(false);
        }
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