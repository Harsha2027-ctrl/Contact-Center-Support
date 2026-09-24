import { Link } from 'react-router-dom';
import { 
  Shield, Headphones, Search, BarChart3, 
  Settings, ArrowRight, CheckCircle, Users, Zap 
} from 'lucide-react';

export default function Home() {
  return (
    <div style={{ background: '#f8fafc', minHeight: '100vh' }}>
      {/* Navbar */}
      <header style={{
        background: 'white',
        borderBottom: '1px solid #e2e8f0',
        padding: '0 24px',
        height: 64,
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
        position: 'sticky',
        top: 0,
        zIndex: 50
      }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: 10 }}>
          <div style={{
            background: 'linear-gradient(135deg, #4f46e5, #7c3aed)',
            width: 36, height: 36, borderRadius: 10,
            display: 'flex', alignItems: 'center', justifyContent: 'center'
          }}>
            <Shield size={20} color="white" />
          </div>
          <strong style={{ fontSize: 17 }}>Pathora</strong>
        </div>

        <div style={{ display: 'flex', gap: 12, alignItems: 'center' }}>
          <Link to="/login" style={{ fontWeight: 500, color: '#64748b' }}>Sign In</Link>
          <Link to="/register" className="btn-primary" style={{ padding: '8px 18px' }}>
            Get Started
          </Link>
        </div>
      </header>

      {/* Hero Section */}
      <section style={{
        maxWidth: 1000,
        margin: '0 auto',
        padding: '80px 20px 60px',
        textAlign: 'center'
      }}>
        <div style={{
          display: 'inline-flex',
          alignItems: 'center',
          gap: 6,
          background: '#eef2ff',
          color: '#4f46e5',
          fontSize: 13,
          fontWeight: 600,
          padding: '6px 14px',
          borderRadius: 20,
          marginBottom: 24
        }}>
          <Zap size={14} /> Intelligent Decision Support for Contact Centers
        </div>

        <h1 style={{
          fontSize: 'clamp(32px, 5vw, 48px)',
          fontWeight: 800,
          lineHeight: 1.2,
          marginBottom: 20,
          color: '#0f172a'
        }}>
          Smarter Support with<br />
          <span style={{ color: '#4f46e5' }}>Guided Decision Trees</span>
        </h1>

        <p style={{
          fontSize: 18,
          color: '#64748b',
          maxWidth: 600,
          margin: '0 auto 36px',
          lineHeight: 1.6
        }}>
          Empower your agents with structured knowledge, real-time guidance, 
          AI-assisted search, and powerful analytics — all in one platform.
        </p>

        <div style={{ display: 'flex', gap: 14, justifyContent: 'center', flexWrap: 'wrap' }}>
          <Link to="/register" className="btn-primary" style={{ padding: '12px 28px', fontSize: 15 }}>
            Get Started Free <ArrowRight size={18} />
          </Link>
          <Link to="/login" className="btn-outline" style={{ padding: '12px 28px', fontSize: 15 }}>
            Sign In
          </Link>
        </div>
      </section>

      {/* Features */}
      <section style={{ maxWidth: 1000, margin: '0 auto', padding: '40px 20px 80px' }}>
        <h2 style={{ textAlign: 'center', fontSize: 28, fontWeight: 700, marginBottom: 12 }}>
          Everything your support team needs
        </h2>
        <p style={{ textAlign: 'center', color: '#64748b', marginBottom: 48 }}>
          Built for Agents, Admins and Supervisors
        </p>

        <div style={{
          display: 'grid',
          gridTemplateColumns: 'repeat(auto-fit, minmax(280px, 1fr))',
          gap: 24
        }}>
          {/* Feature 1 */}
          <div className="card">
            <div style={{
              width: 48, height: 48, borderRadius: 12,
              background: '#eef2ff', display: 'flex',
              alignItems: 'center', justifyContent: 'center', marginBottom: 16
            }}>
              <Headphones size={24} color="#4f46e5" />
            </div>
            <h3 style={{ fontSize: 18, marginBottom: 8 }}>Agent Console</h3>
            <p style={{ color: '#64748b', fontSize: 14, lineHeight: 1.6 }}>
              Guided decision trees help agents resolve customer issues step-by-step with confidence.
            </p>
          </div>

          {/* Feature 2 */}
          <div className="card">
            <div style={{
              width: 48, height: 48, borderRadius: 12,
              background: '#f5f3ff', display: 'flex',
              alignItems: 'center', justifyContent: 'center', marginBottom: 16
            }}>
              <Search size={24} color="#7c3aed" />
            </div>
            <h3 style={{ fontSize: 18, marginBottom: 8 }}>AI-Assisted Search</h3>
            <p style={{ color: '#64748b', fontSize: 14, lineHeight: 1.6 }}>
              Instantly find relevant solutions using intelligent keyword ranking across the knowledge base.
            </p>
          </div>

          {/* Feature 3 */}
          <div className="card">
            <div style={{
              width: 48, height: 48, borderRadius: 12,
              background: '#f0fdfa', display: 'flex',
              alignItems: 'center', justifyContent: 'center', marginBottom: 16
            }}>
              <Settings size={24} color="#0d9488" />
            </div>
            <h3 style={{ fontSize: 18, marginBottom: 8 }}>Admin Panel</h3>
            <p style={{ color: '#64748b', fontSize: 14, lineHeight: 1.6 }}>
              Easily create and manage categories, decision trees, questions, resolutions and options.
            </p>
          </div>

          {/* Feature 4 */}
          <div className="card">
            <div style={{
              width: 48, height: 48, borderRadius: 12,
              background: '#fffbeb', display: 'flex',
              alignItems: 'center', justifyContent: 'center', marginBottom: 16
            }}>
              <BarChart3 size={24} color="#d97706" />
            </div>
            <h3 style={{ fontSize: 18, marginBottom: 8 }}>Analytics Dashboard</h3>
            <p style={{ color: '#64748b', fontSize: 14, lineHeight: 1.6 }}>
              Track resolution rates, escalation metrics, average handling time and most visited nodes.
            </p>
          </div>

          {/* Feature 5 */}
          <div className="card">
            <div style={{
              width: 48, height: 48, borderRadius: 12,
              background: '#fef2f2', display: 'flex',
              alignItems: 'center', justifyContent: 'center', marginBottom: 16
            }}>
              <Users size={24} color="#ef4444" />
            </div>
            <h3 style={{ fontSize: 18, marginBottom: 8 }}>Role-Based Access</h3>
            <p style={{ color: '#64748b', fontSize: 14, lineHeight: 1.6 }}>
              Separate experiences for Agents, Supervisors and Admins with secure JWT authentication.
            </p>
          </div>

          {/* Feature 6 */}
          <div className="card">
            <div style={{
              width: 48, height: 48, borderRadius: 12,
              background: '#ecfdf5', display: 'flex',
              alignItems: 'center', justifyContent: 'center', marginBottom: 16
            }}>
              <CheckCircle size={24} color="#10b981" />
            </div>
            <h3 style={{ fontSize: 18, marginBottom: 8 }}>Session Tracking</h3>
            <p style={{ color: '#64748b', fontSize: 14, lineHeight: 1.6 }}>
              Every agent action is logged for quality, training and continuous improvement.
            </p>
          </div>
        </div>
      </section>

      {/* CTA */}
      <section style={{
        background: 'linear-gradient(135deg, #4f46e5, #7c3aed)',
        padding: '60px 20px',
        textAlign: 'center',
        color: 'white'
      }}>
        <h2 style={{ fontSize: 28, fontWeight: 700, marginBottom: 12 }}>
          Ready to transform your support operations?
        </h2>
        <p style={{ opacity: 0.9, marginBottom: 28, fontSize: 16 }}>
          Start using Pathora today.
        </p>
        <Link to="/register" style={{
          display: 'inline-flex',
          alignItems: 'center',
          gap: 8,
          background: 'white',
          color: '#4f46e5',
          fontWeight: 600,
          padding: '12px 28px',
          borderRadius: 8,
          fontSize: 15
        }}>
          Create Free Account <ArrowRight size={18} />
        </Link>
      </section>

      {/* Footer */}
      <footer style={{
        textAlign: 'center',
        padding: '24px',
        color: '#94a3b8',
        fontSize: 13,
        borderTop: '1px solid #e2e8f0'
      }}>
        © 2026 Pathora · Built for modern support teams
      </footer>
    </div>
  );
}