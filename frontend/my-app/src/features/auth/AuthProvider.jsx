import {createContext, useContext, useState, useEffect} from "react";

const AuthContext = createContext();

export function AuthProvider({children}) {
    const [roles, setRoles] = useState(() => {
        try {
            const storedRoles = sessionStorage.getItem("roles");
            return storedRoles ? JSON.parse(storedRoles) : [];
        } catch (error) {
            console.error("Failed to parse roles from sessionStorage:", error);
            return [];
        }
    });

    const [token, setToken] = useState(sessionStorage.getItem("token") || null);
    const [id_user, setIdUser] = useState(sessionStorage.getItem("id_user") || null);
    const [email, setEmail] = useState(sessionStorage.getItem("email") || null);
    const [name, setName] = useState(sessionStorage.getItem("name") || null);
    const [fullName, setFullName] = useState(sessionStorage.getItem("fullName") || null);
    const [userData, setUserData] = useState(() => {
        try {
            const storedUserData = sessionStorage.getItem("userData");
            return storedUserData ? JSON.parse(storedUserData) : null;
        } catch (error) {
            console.error("Failed to parse userData from sessionStorage:", error);
            return null;
        }
    });

    const login = (newToken, newRoles, newId, newEmail, newName, newFullName, newUserData) => {
        sessionStorage.setItem("id_user", newId);
        sessionStorage.setItem("token", newToken);
        sessionStorage.setItem("roles", JSON.stringify(newRoles));
        sessionStorage.setItem("email", newEmail);
        sessionStorage.setItem("name", newName);
        sessionStorage.setItem("fullName", newFullName);
        sessionStorage.setItem("userData", JSON.stringify(newUserData));

        setEmail(newEmail);
        setToken(newToken);
        setRoles(newRoles);
        setIdUser(newId);
        setName(newName);
        setFullName(newFullName);
        setUserData(newUserData);
    };

    const logout = () => {
        sessionStorage.clear();
        setEmail(null);
        setToken(null);
        setRoles([]);
        setIdUser(null);
        setName(null);
        setFullName(null);
        setUserData(null);
    };

    useEffect(() => {
        const storedToken = sessionStorage.getItem("token");
        const storedRoles = sessionStorage.getItem("roles");
        const storedEmail = sessionStorage.getItem("email");
        const storedId = sessionStorage.getItem("id_user");
        const storedName = sessionStorage.getItem("name");
        const storedFullName = sessionStorage.getItem("fullName");
        const storedUserData = sessionStorage.getItem("userData");

        if (storedToken) setToken(storedToken);
        if (storedEmail) setEmail(storedEmail);
        if (storedId) setIdUser(storedId);
        if (storedName) setName(storedName);
        if (storedFullName) setFullName(storedFullName);

        if (storedRoles) {
            try {
                setRoles(JSON.parse(storedRoles));
            } catch (error) {
                console.error("Failed to parse roles from sessionStorage:", error);
                setRoles([]);
            }
        }

        if (storedUserData) {
            try {
                setUserData(JSON.parse(storedUserData));
            } catch (error) {
                console.error("Failed to parse userData from sessionStorage:", error);
                setUserData(null);
            }
        }
    }, []);

    return (
        <AuthContext.Provider
            value={{
                roles,
                token,
                id_user,
                email,
                name,
                fullName,
                userData,
                login,
                logout
            }}
        >
            {children}
        </AuthContext.Provider>
    );
}

export const useAuthContext = () => useContext(AuthContext);