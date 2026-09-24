import { useState, useEffect } from 'react';
import { getPublishedTrees } from '../api/trees';
import { getNodeWithOptions, getNodesByTree } from '../api/treeNodes';
import { startSession, resolveSession, escalateSession } from '../api/sessions';
import { logStep } from '../api/sessionPaths';
import { 
  Headphones, Play, CheckCircle, AlertTriangle, 
  RotateCcw, MessageSquare, ChevronRight 
} from 'lucide-react';

import { Link } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';

export default function AgentConsole() {
  const [trees, setTrees] = useState([]);
  const [selectedTreeId, setSelectedTreeId] = useState('');
  const [ticketRef, setTicketRef] = useState('');
  const [session, setSession] = useState(null);
  const [currentNode, setCurrentNode] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');

  useEffect(() => {
    getPublishedTrees()
      .then((res) => setTrees(res.data))
      .catch(() => setError('Failed to load trees'));
  }, []);

  const handleStartSession = async () => {
    if (!selectedTreeId) {
      setError('Please select a tree');
      return;
    }
    setLoading(true);
    setError('');
    try {
      const sessionRes = await startSession(selectedTreeId, ticketRef || null);
      setSession(sessionRes.data);
      const nodesRes = await getNodesByTree(selectedTreeId);
      const rootNode = nodesRes.data.find(n => n.nodeType === 'QUESTION') || nodesRes.data[0];
      const nodeWithOptions = await getNodeWithOptions(rootNode.id);
      setCurrentNode(nodeWithOptions.data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to start session');
    } finally {
      setLoading(false);
    }
  };

  const handleOptionClick = async (option) => {
    setLoading(true);
    setError('');
    try {
      await logStep(session.id, currentNode.nodeId, option.optionLabel);
      const nextNodeRes = await getNodeWithOptions(option.nextNodeId);
      setCurrentNode(nextNodeRes.data);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to load next node');
    } finally {
      setLoading(false);
    }
  };

  const handleResolve = async () => {
    try {
      await resolveSession(session.id);
      alert('Session marked as Resolved');
      reset();
    } catch {
      setError('Failed to resolve session');
    }
  };

  const handleEscalate = async () => {
    try {
      await escalateSession(session.id);
      alert('Session Escalated');
      reset();
    } catch {
      setError('Failed to escalate session');
    }
  };

  const reset = () => {
    setSession(null);
    setCurrentNode(null);
    setSelectedTreeId('');
    setTicketRef('');
  };

  return (
    <div>
      <h1 className="page-title">
        <Headphones size={28} color="#4f46e5" /> Agent Console
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
        Start a session and walk through the decision tree to help the customer.
      </p>

      {error && (
        <div style={{ background: '#fef2f2', color: '#b91c1c', padding: 14, borderRadius: 10, marginBottom: 20 }}>
          {error}
        </div>
      )}

      {/* Start Session */}
      {!session && (
        <div className="card" style={{ maxWidth: 520 }}>
          <h3 style={{ marginBottom: 20, display: 'flex', alignItems: 'center', gap: 8 }}>
            <Play size={18} /> Start New Session
          </h3>

          <div style={{ marginBottom: 16 }}>
            <label style={{ display: 'block', fontSize: 13, fontWeight: 500, marginBottom: 6 }}>
              Select Decision Tree
            </label>
            <select
              value={selectedTreeId}
              onChange={(e) => setSelectedTreeId(e.target.value)}
            >
              <option value="">-- Choose a tree --</option>
              {trees.map((tree) => (
                <option key={tree.id} value={tree.id}>
                  {tree.name} ({tree.categoryName})
                </option>
              ))}
            </select>
          </div>

          <div style={{ marginBottom: 24 }}>
            <label style={{ display: 'block', fontSize: 13, fontWeight: 500, marginBottom: 6 }}>
              Ticket Reference (optional)
            </label>
            <input
              value={ticketRef}
              onChange={(e) => setTicketRef(e.target.value)}
              placeholder="e.g. TKT-12345"
            />
          </div>

          <button onClick={handleStartSession} disabled={loading} className="btn-primary">
            <Play size={16} />
            {loading ? 'Starting...' : 'Start Session'}
          </button>
        </div>
      )}

      {/* Active Session */}
      {session && currentNode && (
        <div>
          <div style={{
            display: 'flex', gap: 12, flexWrap: 'wrap',
            marginBottom: 20, fontSize: 14, color: '#64748b'
          }}>
            <span className="badge badge-blue">Session #{session.id}</span>
            <span className="badge badge-green">{session.treeName}</span>
            {session.ticketRef && <span className="badge badge-yellow">Ticket: {session.ticketRef}</span>}
          </div>

          <div className="card">
            {currentNode.nodeType === 'QUESTION' && (
              <>
                <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 12 }}>
                  <MessageSquare size={20} color="#4f46e5" />
                  <h3 style={{ margin: 0 }}>Question</h3>
                </div>
                <p style={{ fontSize: 18, fontWeight: 500, marginBottom: 24, lineHeight: 1.5 }}>
                  {currentNode.questionText}
                </p>

                <div>
                  <strong style={{ fontSize: 13, color: '#64748b', textTransform: 'uppercase', letterSpacing: 0.5 }}>
                    Choose an option
                  </strong>
                  <div style={{ marginTop: 12 }}>
                    {currentNode.options?.map((opt) => (
                      <button
                        key={opt.id}
                        onClick={() => handleOptionClick(opt)}
                        disabled={loading}
                        className="option-btn"
                      >
                        <ChevronRight size={18} />
                        {opt.optionLabel}
                      </button>
                    ))}
                  </div>
                </div>
              </>
            )}

            {currentNode.nodeType === 'LEAF' && (
              <>
                <div style={{ display: 'flex', alignItems: 'center', gap: 8, marginBottom: 12 }}>
                  <CheckCircle size={20} color="#10b981" />
                  <h3 style={{ margin: 0 }}>Resolution</h3>
                </div>
                <p style={{ fontSize: 17, lineHeight: 1.6, marginBottom: 28, whiteSpace: 'pre-wrap' }}>
                  {currentNode.resolutionText}
                </p>

                <div style={{ display: 'flex', gap: 12, flexWrap: 'wrap' }}>
                  <button onClick={handleResolve} className="btn-success">
                    <CheckCircle size={16} /> Mark as Resolved
                  </button>
                  <button onClick={handleEscalate} className="btn-danger">
                    <AlertTriangle size={16} /> Escalate
                  </button>
                </div>
              </>
            )}
          </div>

          <button onClick={reset} style={{ marginTop: 20 }} className="btn-outline">
            <RotateCcw size={16} /> End Session / Start New
          </button>
        </div>
      )}
    </div>
  );
}