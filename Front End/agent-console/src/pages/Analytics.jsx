import { useState, useEffect } from 'react';
import { getAllTrees } from '../api/trees';
import { getTreeAnalytics } from '../api/analytics';
import { 
  BarChart3, Users, CheckCircle, AlertTriangle, 
  Clock, TrendingUp, Activity 
} from 'lucide-react';

import { Link } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';

export default function Analytics() {
  const [trees, setTrees] = useState([]);
  const [selectedTreeId, setSelectedTreeId] = useState('');
  const [data, setData] = useState(null);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    getAllTrees()
      .then(res => setTrees(res.data))
      .catch(() => setError('Failed to load trees'));
  }, []);

  const handleLoad = async () => {
    if (!selectedTreeId) return;
    setLoading(true);
    setError('');
    setData(null);
    try {
      const res = await getTreeAnalytics(selectedTreeId);
      setData(res.data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to load analytics');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1 className="page-title">
        <BarChart3 size={28} color="#d97706" /> Analytics
      </h1>

      <div style={{ marginBottom: 20 }}>
  <Link to="/dashboard" style={{ 
    display: 'inline-flex', 
    alignItems: 'center', 
    gap: 6, 
    fontSize: 14, 
    fontWeight: 500,
    color: '#64748b'
  }}>
    <ArrowLeft size={16} /> Back to Dashboard
  </Link>
</div>

      <p className="page-subtitle">
        Session performance, resolution metrics and most visited nodes.
      </p>

      <div className="card" style={{ marginBottom: 28, display: 'flex', gap: 12, alignItems: 'flex-end', flexWrap: 'wrap' }}>
        <div style={{ flex: 1, minWidth: 220 }}>
          <label style={{ display: 'block', fontSize: 13, fontWeight: 500, marginBottom: 6 }}>
            Select Tree
          </label>
          <select
            value={selectedTreeId}
            onChange={(e) => setSelectedTreeId(e.target.value)}
          >
            <option value="">-- Choose a tree --</option>
            {trees.map(t => (
              <option key={t.id} value={t.id}>{t.name}</option>
            ))}
          </select>
        </div>
        <button onClick={handleLoad} disabled={loading || !selectedTreeId} className="btn-primary">
          <Activity size={16} />
          {loading ? 'Loading...' : 'Load Analytics'}
        </button>
      </div>

      {error && (
        <div style={{ background: '#fef2f2', color: '#b91c1c', padding: 14, borderRadius: 10, marginBottom: 20 }}>
          {error}
        </div>
      )}

      {data && (
        <>
          <h2 style={{ fontSize: 20, marginBottom: 16 }}>{data.treeName}</h2>

          <div className="grid-2" style={{ marginBottom: 28 }}>
            <div className="card" style={{ display: 'flex', alignItems: 'center', gap: 16 }}>
              <div style={{ width: 48, height: 48, borderRadius: 12, background: '#eef2ff', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <Users size={22} color="#4f46e5" />
              </div>
              <div>
                <div style={{ fontSize: 13, color: '#64748b' }}>Total Sessions</div>
                <div style={{ fontSize: 24, fontWeight: 700 }}>{data.totalSessions}</div>
              </div>
            </div>

            <div className="card" style={{ display: 'flex', alignItems: 'center', gap: 16 }}>
              <div style={{ width: 48, height: 48, borderRadius: 12, background: '#d1fae5', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <CheckCircle size={22} color="#10b981" />
              </div>
              <div>
                <div style={{ fontSize: 13, color: '#64748b' }}>Resolved</div>
                <div style={{ fontSize: 24, fontWeight: 700 }}>{data.resolvedCount}</div>
              </div>
            </div>

            <div className="card" style={{ display: 'flex', alignItems: 'center', gap: 16 }}>
              <div style={{ width: 48, height: 48, borderRadius: 12, background: '#fee2e2', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <AlertTriangle size={22} color="#ef4444" />
              </div>
              <div>
                <div style={{ fontSize: 13, color: '#64748b' }}>Escalated</div>
                <div style={{ fontSize: 24, fontWeight: 700 }}>{data.escalatedCount}</div>
              </div>
            </div>

            <div className="card" style={{ display: 'flex', alignItems: 'center', gap: 16 }}>
              <div style={{ width: 48, height: 48, borderRadius: 12, background: '#fef3c7', display: 'flex', alignItems: 'center', justifyContent: 'center' }}>
                <Clock size={22} color="#d97706" />
              </div>
              <div>
                <div style={{ fontSize: 13, color: '#64748b' }}>Avg Resolution Time</div>
                <div style={{ fontSize: 24, fontWeight: 700 }}>
                  {data.avgResolutionTimeSeconds?.toFixed(1)}s
                </div>
              </div>
            </div>
          </div>

          <div className="card" style={{ marginBottom: 20 }}>
            <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 12 }}>
              <TrendingUp size={18} color="#4f46e5" />
              <h3 style={{ margin: 0 }}>More Metrics</h3>
            </div>
            <div style={{ display: 'flex', gap: 32, flexWrap: 'wrap' }}>
              <div>
                <div style={{ fontSize: 13, color: '#64748b' }}>In Progress</div>
                <div style={{ fontSize: 20, fontWeight: 600 }}>{data.inProgressCount}</div>
              </div>
              <div>
                <div style={{ fontSize: 13, color: '#64748b' }}>Escalation Rate</div>
                <div style={{ fontSize: 20, fontWeight: 600 }}>
                  {data.escalationRatePercent?.toFixed(1)}%
                </div>
              </div>
            </div>
          </div>

          {data.mostVisitedNodes?.length > 0 && (
            <div className="card">
              <h3 style={{ marginBottom: 16 }}>Most Visited Nodes</h3>
              <div style={{ display: 'flex', flexDirection: 'column', gap: 10 }}>
                {data.mostVisitedNodes.map((n, i) => (
                  <div key={i} style={{
                    display: 'flex', justifyContent: 'space-between', alignItems: 'center',
                    padding: '10px 14px', background: '#f8fafc', borderRadius: 8
                  }}>
                    <span style={{ fontSize: 14 }}>
                      {n.nodeLabel || `Node ${n.nodeId}`}
                    </span>
                    <span className="badge badge-blue">{n.visitCount} visits</span>
                  </div>
                ))}
              </div>
            </div>
          )}
        </>
      )}
    </div>
  );
}