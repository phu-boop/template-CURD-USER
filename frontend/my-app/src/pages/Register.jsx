import {useState} from "react"
import Button from "../components/Button"
import Input from "../components/Input"
import {registerUser} from "../services/auth/authService.js"

export default function Register() {
    const [form, setForm] = useState({
        name: "",
        email: "",
        password: "",
        fullName: "",
        phone: "",
        address: "",
        birthday: "",
        gender: ""
    })

    const [message, setMessage] = useState("")
    const [isError, setIsError] = useState(false)

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value})
    }

    const handleSubmit = async (e) => {
        e.preventDefault()
        try {
            const respond = await registerUser(form)

            if (respond.code === "1000") {
                setIsError(false)
                setMessage(respond.message)
            } else {
                setIsError(true)
                setMessage(respond.message)
            }
        } catch (error) {
            setIsError(true)
            setMessage("Đã có lỗi xảy ra, vui lòng thử lại!")
        }
    }

    return (
        <div className="flex items-center justify-center min-h-screen bg-slate-100">
            <form
                onSubmit={handleSubmit}
                className="bg-white p-6 rounded-xl shadow-lg w-full max-w-sm space-y-4"
            >
                <h2 className="text-2xl font-bold text-center text-sky-600">Đăng ký</h2>

                <Input
                    type="text"
                    name="name"
                    placeholder="Tên tài khoản"
                    value={form.name}
                    onChange={handleChange}
                />
                <Input
                    type="text"
                    name="fullName"
                    placeholder="Họ và tên đầy đủ"
                    value={form.fullName}
                    onChange={handleChange}
                />
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
                <Input
                    type="text"
                    name="phone"
                    placeholder="Số điện thoại"
                    value={form.phone}
                    onChange={handleChange}
                />
                <Input
                    type="text"
                    name="address"
                    placeholder="Địa chỉ"
                    value={form.address}
                    onChange={handleChange}
                />
                <Input
                    type="date"
                    name="birthday"
                    value={form.birthday}
                    onChange={handleChange}
                />
                <select
                    name="gender"
                    value={form.gender}
                    onChange={handleChange}
                    className="w-full px-4 py-2 border border-slate-300 rounded-lg focus:ring-2 focus:ring-sky-400"
                >
                    <option value="">-- Chọn giới tính --</option>
                    <option value="MALE">Nam</option>
                    <option value="FEMALE">Nữ</option>
                    <option value="OTHER">Khác</option>
                </select>

                {/* Thông báo */}
                {message && (
                    <p className={`text-center text-sm ${isError ? "text-red-500" : "text-green-600"}`}>
                        {message}
                    </p>
                )}

                <Button variant="primary" size="sm" type="submit" className="w-full">
                    Đăng ký
                </Button>
            </form>
        </div>
    )
}
