import React, {useState} from "react";
import axios from "axios";
import {getAdmin,getUser} from "../services/test.js";
export default function Test() {
    const [message, setMessage] = useState("");

    const callApi = async (path) => {
        try {
            const response = await getAdmin();
            setMessage(response.data.message); // lấy message từ ApiRespond
        } catch (error) {
            console.error(error);
            if (error.response) {
                setMessage("Error: " + error.response.data.message);
            } else {
                setMessage("Request failed");
            }
        }
    };

    return (
        <div className="p-6 flex flex-col items-center gap-4">
            <h1 className="text-2xl font-bold">Test API</h1>
            <div className="flex gap-4">
                <button
                    className="px-4 py-2 bg-blue-500 text-white rounded-lg shadow"
                    onClick={() => callApi("admin")}
                >
                    Call Admin API
                </button>
                <button
                    className="px-4 py-2 bg-green-500 text-white rounded-lg shadow"
                    onClick={() => callApi("user")}
                >
                    Call User API
                </button>
            </div>
            {message && (
                <div className="mt-4 p-3 bg-gray-100 border rounded-lg shadow">
                    <p className="text-lg">{message}</p>
                </div>
            )}
        </div>
    );
}
