import { useEffect, useState } from 'react'
import api from '../api'

export default function MyTickets() {
  const [data, setData] = useState<any[]>([])
  const [error, setError] = useState<string | null>(null)

  useEffect(() => {
    (async () => {
      try {
        const res = await api.get('/api/trains/me')
        setData(res.data)
      } catch (err: any) {
        setError(err?.response?.data?.error || 'Failed to load tickets')
      }
    })()
  }, [])

  return (
    <div style={{padding:16}}>
      <h2>My Tickets</h2>
      {error && <div style={{color:'red'}}>{error}</div>}
      <table border={1} cellPadding={6}>
        <thead>
          <tr><th>PNR</th><th>Train</th><th>From</th><th>To</th><th>Date</th><th>Seat</th></tr>
        </thead>
        <tbody>
        {data.map(t => (
          <tr key={t.pnrNumber}>
            <td>{t.pnrNumber}</td>
            <td>{t.trainName} ({t.trainNumber})</td>
            <td>{t.source}</td>
            <td>{t.destination}</td>
            <td>{t.journeyDate}</td>
            <td>{t.seatNumber}</td>
          </tr>
        ))}
        </tbody>
      </table>
    </div>
  )
}
