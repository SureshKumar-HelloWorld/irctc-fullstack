import { Routes, Route, Link, Navigate } from 'react-router-dom'
import Login from './pages/Login'
import Register from './pages/Register'
import Book from './pages/Book'
import MyTickets from './pages/MyTickets'
import AdminTickets from './pages/AdminTickets'
import { useAuth } from './auth'

function Nav() {
  const { token, roles, logout } = useAuth()
  return (
    <nav style={{display:'flex', gap:12, padding:12, borderBottom:'1px solid #ddd'}}>
      <Link to="/">Home</Link>
      {token && <Link to="/book">Book</Link>}
      {token && <Link to="/me">My Tickets</Link>}
      {roles.includes('ADMIN') && <Link to="/admin/tickets">Admin</Link>}
      {!token && <Link to="/login">Login</Link>}
      {!token && <Link to="/register">Register</Link>}
      {token && <button onClick={logout}>Logout</button>}
    </nav>
  )
}

function Home() {
  return <div style={{padding:16}}><h2>IRCTC Train Booking</h2><p>Book, view and manage your train tickets.</p></div>
}

function PrivateRoute({ children }: { children: JSX.Element }) {
  const { token } = useAuth()
  return token ? children : <Navigate to="/login" />
}

function AdminRoute({ children }: { children: JSX.Element }) {
  const { token, roles } = useAuth()
  return token && roles.includes('ADMIN') ? children : <Navigate to="/" />
}

export default function App() {
  return (
    <div>
      <Nav />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        <Route path="/book" element={<PrivateRoute><Book /></PrivateRoute>} />
        <Route path="/me" element={<PrivateRoute><MyTickets /></PrivateRoute>} />
        <Route path="/admin/tickets" element={<AdminRoute><AdminTickets /></AdminRoute>} />
      </Routes>
    </div>
  )
}
