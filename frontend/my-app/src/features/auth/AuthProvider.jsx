import { createContext, useContext, useState, useEffect } from "react";

const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [role, setRole] = useState(sessionStorage.getItem("role") || null);
  const [token, setToken] = useState(sessionStorage.getItem("token") || null);
  const [id_user, setIdUser] = useState(sessionStorage.getItem("id_user") || null);
  const [email, setEmail] = useState(sessionStorage.getItem("email") || null);

  const login = (newToken, newRole, newId, email) => {
    sessionStorage.setItem("id_user", newId);
    sessionStorage.setItem("token", newToken);
    sessionStorage.setItem("role", newRole);
    sessionStorage.setItem("email", email);
    setEmail(email);
    setToken(newToken);
    setRole(newRole);
    setIdUser(newId);
  };

  const logout = () => {
    sessionStorage.clear();
    setEmail(null);
    setToken(null);
    setRole(null);
    setIdUser(null);
  };

  useEffect(() => {
    const storedToken = sessionStorage.getItem("token");
    const storedRole = sessionStorage.getItem("role");
    if (storedToken) setToken(storedToken);
    if (storedRole) setRole(storedRole);
  }, []);

  return (
    <AuthContext.Provider value={{ role, token, id_user, email, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}

export const useAuthContext = () => useContext(AuthContext);
