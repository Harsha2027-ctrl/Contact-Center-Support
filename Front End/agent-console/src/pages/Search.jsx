import { useState } from 'react';
import { search } from '../api/search';
import { Search as SearchIcon, Sparkles, FileText, HelpCircle } from 'lucide-react';
import { Link } from 'react-router-dom';
import { ArrowLeft } from 'lucide-react';

export default function SearchPage() {
  const [query, setQuery] = useState('');
  const [results, setResults] = useState([]);
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);
  const [searched, setSearched] = useState(false);

  const handleSearch = async (e) => {
    e.preventDefault();
    if (!query.trim()) return;
    setLoading(true);
    setError('');
    setResults([]);
    setSearched(true);
    try {
      const res = await search(query);
      setResults(res.data || []);
    } catch (err) {
      setError(err.response?.data?.message || 'Search failed');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div>
      <h1 className="page-title">
        <Sparkles size={28} color="#7c3aed" /> AI-Assisted Search
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
        Intelligent keyword ranking across all questions and resolutions in the knowledge base.
      </p>

      {/* Search Box */}
      <form onSubmit={handleSearch} style={{ marginBottom: 32 }}>
        <div style={{
          display: 'flex', gap: 12, background: 'white',
          padding: 8, borderRadius: 14, border: '1px solid #e2e8f0',
          boxShadow: '0 4px 6px -1px rgb(0 0 0 / 0.05)'
        }}>
          <div style={{ position: 'relative', flex: 1 }}>
            <SearchIcon size={18} style={{ position: 'absolute', left: 14, top: 12, color: '#94a3b8' }} />
            <input
              value={query}
              onChange={(e) => setQuery(e.target.value)}
              placeholder="Try: refund, subscription, payment, cancel..."
              style={{ paddingLeft: 44, border: 'none', boxShadow: 'none', fontSize: 16 }}
            />
          </div>
          <button type="submit" disabled={loading || !query.trim()} className="btn-primary" style={{ padding: '10px 24px' }}>
            {loading ? 'Searching...' : 'Search'}
          </button>
        </div>
      </form>

      {error && (
        <div style={{ background: '#fef2f2', color: '#b91c1c', padding: 14, borderRadius: 10, marginBottom: 20 }}>
          {error}
        </div>
      )}

      {loading && (
        <div style={{ textAlign: 'center', padding: 48, color: '#64748b' }}>
          Analyzing knowledge base...
        </div>
      )}

      {!loading && searched && results.length === 0 && (
        <div className="card" style={{ textAlign: 'center', padding: 48 }}>
          <p style={{ fontSize: 16, color: '#64748b' }}>No matching results found.</p>
          <p style={{ fontSize: 14, color: '#94a3b8', marginTop: 6 }}>
            Try different keywords like “refund” or “subscription”.
          </p>
        </div>
      )}

      {!loading && results.length > 0 && (
        <div>
          <p style={{ marginBottom: 16, color: '#64748b' }}>
            Found <strong>{results.length}</strong> relevant result{results.length > 1 ? 's' : ''}
          </p>
          <div style={{ display: 'flex', flexDirection: 'column', gap: 14 }}>
            {results.map((r, i) => (
              <div key={i} className="card">
                <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', marginBottom: 12 }}>
                  <div style={{ display: 'flex', gap: 8, alignItems: 'center' }}>
                    {r.nodeType === 'QUESTION' ? (
                      <HelpCircle size={16} color="#4f46e5" />
                    ) : (
                      <FileText size={16} color="#10b981" />
                    )}
                    <span className={`badge ${r.nodeType === 'QUESTION' ? 'badge-blue' : 'badge-green'}`}>
                      {r.nodeType}
                    </span>
                    <span style={{ color: '#64748b', fontSize: 14 }}>{r.treeName}</span>
                  </div>
                  <span className="badge badge-yellow">
                    {(r.relevanceScore * 100).toFixed(0)}% match
                  </span>
                </div>
                <p style={{ fontSize: 15, lineHeight: 1.6, margin: 0 }}>{r.matchedText}</p>
              </div>
            ))}
          </div>
        </div>
      )}

      {!searched && !loading && (
        <div style={{
          background: '#f5f3ff', border: '1px solid #ddd6fe',
          borderRadius: 12, padding: 20, color: '#5b21b6'
        }}>
          <strong>How it works:</strong> The system removes common words, matches your query against every node, 
          and ranks results by relevance score using intelligent keyword overlap.
        </div>
      )}
    </div>
  );
}