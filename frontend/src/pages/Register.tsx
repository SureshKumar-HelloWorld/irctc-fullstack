import { useState } from 'react'
import api from '../api'
import { useAuth } from '../auth'
import { useNavigate } from 'react-router-dom'

export default function Register() {
  const [name, setName] = useState('Suresh Kumar')
  const [email, setEmail] = useState('user@example.com')
  const [password, setPassword] = useState('password')
  const [error, setError] = useState<string | null>(null)
  const { login } = useAuth()
  const nav = useNavigate()

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)
    try {
      const res = await api.post('/api/auth/register', { name, email, password })
      login(res.data.token, res.data.roles, res.data.email)
      nav('/')
    } catch (err: any) {
      setError(err?.response?.data?.error || 'Register failed')
    }
  }

  return (
    <div style={{padding:16}}>
      <h2>Register</h2>
      <form onSubmit={onSubmit} style={{display:'grid', gap:8, maxWidth:360}}>
        <input value={name} onChange={e=>setName(e.target.value)} placeholder="Name" />
        <input value={email} onChange={e=>setEmail(e.target.value)} placeholder="Email" />
        <input type="password" value={password} onChange={e=>setPassword(e.target.value)} placeholder="Password" />
        <button type="submit">Create account</button>
        {error && <div style={{color:'red'}}>{error}</div>}
      </form>
    </div>
  )
}
