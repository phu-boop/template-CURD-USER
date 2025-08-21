import Button from "../components/Button"
import { useNavigate } from "react-router-dom"
export default function Home() {
    const navigate = useNavigate()
  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-950 text-white">
      {/* Hero section */}
      <section className="flex flex-col items-center justify-center text-center px-6 py-20">
        <h1 className="text-4xl md:text-6xl font-bold mb-6">
          Chào mừng đến với <span className="text-sky-400">LearnX</span>
        </h1>
        <p className="text-lg md:text-xl text-slate-300 max-w-2xl mb-8">
          Nền tảng học trực tuyến hiện đại, kết nối bạn với khóa học chất lượng
          và giảng viên hàng đầu.
        </p>
        <div className="flex gap-4">
            <Button
                onClick={() => {navigate('/login')}}
                className={"px-6 py-3 bg-sky-500 hover:bg-sky-400 rounded-lg text-white font-semibold transition"}
                >
                Bắt đầu học
            </Button>
            <Button
                className={"px-6 py-3 bg-slate-700 hover:bg-slate-600 rounded-lg text-white font-semibold transition"}
                onClick={() => {navigate('/login')}}
                >
                login
            </Button>
        </div>
      </section>

      {/* Features */}
      <section className="px-6 py-16 bg-slate-900/60 backdrop-blur">
        <h2 className="text-3xl font-bold text-center mb-12">
          Tại sao chọn LearnX?
        </h2>
        <div className="grid md:grid-cols-3 gap-8 max-w-6xl mx-auto">
          <div className="p-6 bg-slate-800 rounded-xl shadow-lg hover:shadow-sky-500/30 transition">
            <h3 className="text-xl font-semibold mb-3">🎓 Khóa học đa dạng</h3>
            <p className="text-slate-300">
              Học từ lập trình, thiết kế đến kỹ năng mềm, tất cả đều có trên nền
              tảng.
            </p>
          </div>
          <div className="p-6 bg-slate-800 rounded-xl shadow-lg hover:shadow-sky-500/30 transition">
            <h3 className="text-xl font-semibold mb-3">👨‍🏫 Giảng viên chất lượng</h3>
            <p className="text-slate-300">
              Được hướng dẫn bởi giảng viên giàu kinh nghiệm trong ngành.
            </p>
          </div>
          <div className="p-6 bg-slate-800 rounded-xl shadow-lg hover:shadow-sky-500/30 transition">
            <h3 className="text-xl font-semibold mb-3">⚡ Học mọi lúc mọi nơi</h3>
            <p className="text-slate-300">
              Nền tảng thân thiện, hỗ trợ cả web và mobile, giúp bạn học mọi lúc.
            </p>
          </div>
        </div>
      </section>

      {/* Footer */}
      <footer className="py-6 text-center text-slate-400 text-sm border-t border-slate-700">
        © {new Date().getFullYear()} LearnX. All rights reserved.
      </footer>
    </div>
  )
}
