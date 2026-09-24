import { Link, useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { 
  LayoutDashboard, Headphones, Settings, BarChart3, 
  Search, LogOut, Shield 
} from 'lucide-react';

export default function Layout({ children }) {
  const { user, logout } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();

  const handleLogout = () => {
    logout();
    navigate('/');          // Go to public homepage after logout
  };

  const isActive = (path) => location.pathname === path;

  const navLink = (path, icon, label) => (
    <Link
      to={path}
      style={{
        display: 'flex',
        alignItems: 'center',
        gap: 6,
        padding: '8px 14px',
        borderRadius: 8,
        fontSize: 14,
        fontWeight: isActive(path) ? 600 : 500,
        background: isActive(path) ? '#eef2ff' : 'transparent',
        color: isActive(path) ? '#4f46e5' : '#64748b',
        transition: 'all 0.15s'
      }}
    >
      {icon}
      {label}
    </Link>
  );

  return (
    <div>
      <header className="header">
        <div style={{ display: 'flex', alignItems: 'center', gap: 12 }}>
          <div style={{
            background: 'linear-gradient(135deg, #4f46e5, #7c3aed)',
            width: 38, height: 38, borderRadius: 10,
            display: 'flex', alignItems: 'center', justifyContent: 'center'
          }}>
            <Shield size={20} color="white" />
          </div>
          <div>
            <strong style={{ fontSize: 16 }}>Pathora</strong>
            {user && (
              <div style={{ fontSize: 12, color: '#64748b' }}>
                {user.name} · {user.role}
              </div>
            )}
          </div>
        </div>

        <nav className="nav">
          {user && (
            <>
              {navLink('/dashboard', <LayoutDashboard size={16} />, 'Dashboard')}
              {navLink('/agent', <Headphones size={16} />, 'Agent Console')}
              {(user.role === 'ADMIN' || user.role === 'SUPERVISOR') && 
                navLink('/admin', <Settings size={16} />, 'Admin')}
              {(user.role === 'ADMIN' || user.role === 'SUPERVISOR') && 
                navLink('/analytics', <BarChart3 size={16} />, 'Analytics')}
              {navLink('/search', <Search size={16} />, 'AI Search')}

              <button 
                onClick={handleLogout} 
                style={{ 
                  marginLeft: 8, 
                  display: 'flex', 
                  alignItems: 'center', 
                  gap: 6,
                  color: '#64748b'
                }}
              >
                <LogOut size={16} /> Logout
              </button>
            </>
          )}
        </nav>
      </header>

      <main className="container">
        {children}
      </main>
    </div>
  );
}