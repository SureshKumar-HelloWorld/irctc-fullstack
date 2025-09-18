import React, { createContext, useContext, useEffect, useState } from 'react'
import axios from 'axios'

type AuthContextType = {
  token: string | null
  roles: string[]
  email: string | null
  login: (t: string, r: string[], e: string) => void
  logout: () => void
}

const AuthContext = createContext<AuthContextType>({
  token: null, roles: [], email: null, login: () => {}, logout: () => {}
})

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [token, setToken] = useState<string | null>(() => localStorage.getItem('token'))
  const [roles, setRoles] = useState<string[]>(() => JSON.parse(localStorage.getItem('roles') || '[]'))
  const [email, setEmail] = useState<string | null>(() => localStorage.getItem('email'))

  useEffect(() => {
    const instance = axios.create()
    axios.defaults.baseURL = import.meta.env.VITE_API_BASE || 'http://localhost:8080'
    axios.interceptors.request.use(config => {
      if (token) config.headers.Authorization = `Bearer ${token}`
      return config
    })
  }, [token])

  const login = (t: string, r: string[], e: string) => {
    setToken(t); setRoles(r); setEmail(e)
    localStorage.setItem('token', t)
    localStorage.setItem('roles', JSON.stringify(r))
    localStorage.setItem('email', e)
  }

  const logout = () => {
    setToken(null); setRoles([]); setEmail(null)
    localStorage.removeItem('token')
    localStorage.removeItem('roles')
    localStorage.removeItem('email')
  }

  return <AuthContext.Provider value={{ token, roles, email, login, logout }}>{children}</AuthContext.Provider>
}

export const useAuth = () => useContext(AuthContext)
