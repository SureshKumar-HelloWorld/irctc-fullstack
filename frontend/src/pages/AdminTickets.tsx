import { useEffect, useState } from 'react'
import api from '../api'

export default function AdminTickets() {
  const [data, setData] = useState<any[]>([])
  const [error, setError] = useState<string | null>(null)

  const load = async () => {
    try {
      const res = await api.get('/api/admin/tickets')
      setData(res.data)
    } catch (err: any) {
      setError(err?.response?.data?.error || 'Failed to load tickets')
    }
  }

  const cancel = async (pnr: number) => {
    try {
      await api.delete(`/api/admin/tickets/${pnr}`)
      await load()
    } catch (err: any) {
      setError(err?.response?.data?.error || 'Failed to cancel')
    }
  }

  useEffect(() => { load() }, [])

  return (
    <div style={{padding:16}}>
      <h2>Admin: All Tickets</h2>
      {error && <div style={{color:'red'}}>{error}</div>}
      <table border={1} cellPadding={6}>
        <thead>
          <tr><th>PNR</th><th>Passenger</th><th>Train</th><th>From</th><th>To</th><th>Date</th><th>Seat</th><th>Action</th></tr>
        </thead>
        <tbody>
        {data.map(t => (
          <tr key={t.pnrNumber}>
            <td>{t.pnrNumber}</td>
            <td>{t.passengerName}</td>
            <td>{t.trainName} ({t.trainNumber})</td>
            <td>{t.source}</td>
            <td>{t.destination}</td>
            <td>{t.journeyDate}</td>
            <td>{t.seatNumber}</td>
            <td><button onClick={() => cancel(t.pnrNumber)}>Cancel</button></td>
          </tr>
        ))}
        </tbody>
      </table>
    </div>
  )
}
