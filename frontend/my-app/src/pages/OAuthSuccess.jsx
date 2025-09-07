// /oauth-success.jsx
import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

export default function OAuthSuccess() {
  const navigate = useNavigate();

  useEffect(() => {
    const params = new URLSearchParams(window.location.search);
    const accessToken = params.get("accessToken");

    if (accessToken) {
      localStorage.setItem("accessToken", accessToken);

      // gọi API lấy user info
      fetch("http://localhost:8080/auth/me", {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
        credentials: "include", // để gửi cookie refreshToken
      })
        .then(res => {
          if (!res.ok) throw new Error("Unauthorized");
          return res.json();
        })
        .then(data => {
          localStorage.setItem("user", JSON.stringify(data));
          console.log(data);
        })
        .catch(err => {
          console.error("Error fetching user:", err);
          navigate("/login");
        });
    } else {
      navigate("/login");
    }
  }, [navigate]);

  return <p>Đang đăng nhập...</p>;
}
