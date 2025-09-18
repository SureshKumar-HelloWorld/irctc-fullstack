import { useState } from 'react'
import api from '../api'

export default function Book() {
  const [form, setForm] = useState({
    passengerName: 'Suresh',
    passengerAge: 31,
    journeyDate: '2025-09-10',
    passengerEmail: 'suresh@example.com',
    source: 'Hyderabad',
    destination: 'Bengaluru',
    trainName: 'Shatabdi',
    trainNumber: 12026,
    departureTime: '07:30:00',
    arrivalTime: '12:45:00',
    seatNumber: 21
  })
  const [result, setResult] = useState<any>(null)
  const [error, setError] = useState<string | null>(null)

  const onChange = (e: any) => setForm({ ...form, [e.target.name]: e.target.value })

  const onSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    setError(null)
    try {
      const res = await api.post('/api/trains/book', form)
      setResult(res.data)
    } catch (err: any) {
      setError(err?.response?.data?.error || 'Booking failed')
    }
  }

  return (
    <div style={{padding:16}}>
      <h2>Book Ticket</h2>
      <form onSubmit={onSubmit} style={{display:'grid', gap:8, maxWidth:480}}>
        {Object.entries(form).map(([k,v]) => (
          <input key={k} name={k} value={v as any} onChange={onChange} placeholder={k} />
        ))}
        <button type="submit">Book</button>
        {error && <div style={{color:'red'}}>{error}</div>}
      </form>

      {result && (
        <pre style={{background:'#f7f7f7', padding:12, marginTop:12}}>{JSON.stringify(result, null, 2)}</pre>
      )}
    </div>
  )
}
