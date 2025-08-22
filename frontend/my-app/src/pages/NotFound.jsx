import {Link} from "react-router-dom"

export default function NotFound() {
    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-slate-100">
            <h1 className="text-6xl font-bold text-sky-600 mb-4">404</h1>
            <p className="text-xl text-gray-600 mb-6">
                Oops! Trang bạn tìm không tồn tại.
            </p>
            <Link
                to="/"
                className="px-6 py-2 bg-sky-600 text-white rounded-lg shadow hover:bg-sky-700 transition"
            >
                Quay về Trang chủ
            </Link>
        </div>
    )
}
