import { useState } from "react";
import reactLogo from "./assets/react.svg";
import viteLogo from "/vite.svg";

function App() {
    const [count, setCount] = useState(0);

    return (
        <div className="flex flex-col items-center justify-center min-h-screen bg-gray-900 text-white">
            {/* Logo */}
            <div className="flex gap-8 mb-8">
                <a href="https://vite.dev" target="_blank">
                    <img src={viteLogo} className="w-20 h-20 hover:scale-110 transition-transform" alt="Vite logo" />
                </a>
                <a href="https://react.dev" target="_blank">
                    <img src={reactLogo} className="w-20 h-20 hover:scale-110 transition-transform" alt="React logo" />
                </a>
            </div>

            {/* Title */}
            <h1 className="text-4xl font-bold mb-6">Vite + React + Tailwind 🚀</h1>

            {/* Card */}
            <div className="bg-gray-800 p-6 rounded-2xl shadow-lg text-center">
                <button
                    onClick={() => setCount((count) => count + 1)}
                    className="px-6 py-2 bg-blue-600 hover:bg-blue-700 rounded-lg text-lg font-semibold transition-colors"
                >
                    count is {count}
                </button>
                <p className="mt-4 text-gray-300">
                    Edit <code className="bg-gray-700 px-1 py-0.5 rounded">src/App.tsx</code> and save to test HMR
                </p>
            </div>

            {/* Docs link */}
            <p className="mt-6 text-gray-400">
                Click on the Vite and React logos to learn more
            </p>
        </div>
    );
}

export default App;
