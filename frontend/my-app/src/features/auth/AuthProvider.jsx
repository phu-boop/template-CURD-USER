import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [roles, setRoles] = useState(
    JSON.parse(sessionStorage.getItem("roles")) || [] // ✅ parse về mảng
  );
  const [token, setToken] = useState(sessionStorage.getItem("token") || null);
  const [id_user, setIdUser] = useState(sessionStorage.getItem("id_user") || null);
  const [email, setEmail] = useState(sessionStorage.getItem("email") || null);

  const login = (newToken, newRoles, newId, newEmail) => {
    sessionStorage.setItem("id_user", newId);
    sessionStorage.setItem("token", newToken);
    sessionStorage.setItem("roles", JSON.stringify(newRoles));
    sessionStorage.setItem("email", newEmail);

    setEmail(newEmail);
    setToken(newToken);
    setRoles(newRoles);
    setIdUser(newId);
  };

  const logout = () => {
    sessionStorage.clear();
    setEmail(null);
    setToken(null);
    setRoles([]);
    setIdUser(null);
  };

  useEffect(() => {
    const storedToken = sessionStorage.getItem("token");
    const storedRoles = sessionStorage.getItem("roles");
    if (storedToken) setToken(storedToken);
    if (storedRoles) setRoles(JSON.parse(storedRoles));
  }, []);

  return (
    <AuthContext.Provider
      value={{ roles, token, id_user, email, login, logout }}
    >
      {children}
    </AuthContext.Provider>
  );
}

export const useAuthContext = () => useContext(AuthContext);
