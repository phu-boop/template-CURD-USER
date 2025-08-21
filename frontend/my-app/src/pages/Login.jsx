import {useState} from "react"
import Button from "../components/Button.jsx"
import Input from "../components/Input.jsx"
import {loginUser} from "../services/auth/authService.js"
import Alert from "../components/Alert.jsx"
import Swal from "sweetalert2"
import {useNavigate} from "react-router-dom"
import {useAuthContext} from "../features/auth/AuthProvider"

export default function Login() {
    const navigate = useNavigate()
    const {login} = useAuthContext()
    const [form, setForm] = useState({email: "", password: ""})
    const [error, setError] = useState(null)
    const [loading, setLoading] = useState(false)

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value})
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        setLoading(true)
        setError(null)

        try {
            const data = await loginUser(form)
            const Respond = data.data

            if (data.code === "1000") {
                login(
                    Respond.token,
                    Respond.userRespond.roles,
                    Respond.userRespond.id,
                    Respond.userRespond.email
                )

                Swal.fire({
                    title: "Chúc mừng!",
                    text: "Bạn đã đăng nhập thành công!",
                    icon: "success",
                    confirmButtonText: "OK"
                }).then((result) => {
                    if (result.isConfirmed) {
                        if (Respond.userRespond.roles.includes("Admin")) {
                            navigate("/admin")
                        } else {
                            navigate("/")
                        }
                    }
                })
            } else {
                setError(data.message)
            }

        } catch (err) {
            console.error(err)
            setError(err.message || "Đăng nhập thất bại")
        } finally {
            setLoading(false)
        }
    }

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
                />
                <Input
                    type="password"
                    name="password"
                    placeholder="Mật khẩu"
                    value={form.password}
                    onChange={handleChange}
                />
                {error && (
                    <Alert type="error" message={error} onClose={() => setError(null)}/>
                )}
                <Button variant="primary" size="sm" disabled={loading}>
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
    )
}
