import { Link } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import { 
  Headphones, Settings, BarChart3, Search, 
  ArrowRight, Sparkles 
} from 'lucide-react';

export default function Dashboard() {
  const { user } = useAuth();

  const cards = [
    {
      title: 'Agent Console',
      desc: 'Walk decision trees and handle live customer sessions',
      icon: <Headphones size={24} color="#4f46e5" />,
      to: '/agent',
      color: '#eef2ff'
    },
    {
      title: 'AI-Assisted Search',
      desc: 'Intelligent keyword search across the entire knowledge base',
      icon: <Search size={24} color="#7c3aed" />,
      to: '/search',
      color: '#f5f3ff',
      badge: 'AI'
    },
    {
      title: 'Admin Panel',
      desc: 'Manage categories, trees, nodes and options',
      icon: <Settings size={24} color="#0d9488" />,
      to: '/admin',
      color: '#f0fdfa',
      roles: ['ADMIN', 'SUPERVISOR']
    },
    {
      title: 'Analytics',
      desc: 'Session metrics, resolution time and most visited nodes',
      icon: <BarChart3 size={24} color="#d97706" />,
      to: '/analytics',
      color: '#fffbeb',
      roles: ['ADMIN', 'SUPERVISOR']
    }
  ];

  return (
    <div>
      <h1 className="page-title">
        Welcome back, {user?.name}
      </h1>
      <p className="page-subtitle">
        You are logged in as <strong>{user?.role}</strong>. Choose a module below to get started.
      </p>

      <div className="grid-2">
        {cards
          .filter(c => !c.roles || c.roles.includes(user?.role))
          .map((card) => (
            <Link key={card.to} to={card.to} style={{ textDecoration: 'none' }}>
              <div className="card" style={{ 
                height: '100%', 
                cursor: 'pointer',
                border: card.badge ? '2px solid #7c3aed' : undefined
              }}>
                <div style={{ 
                  display: 'flex', 
                  justifyContent: 'space-between', 
                  alignItems: 'flex-start',
                  marginBottom: 16 
                }}>
                  <div style={{
                    width: 48, height: 48, borderRadius: 12,
                    background: card.color,
                    display: 'flex', alignItems: 'center', justifyContent: 'center'
                  }}>
                    {card.icon}
                  </div>
                  {card.badge && (
                    <span className="badge badge-blue" style={{ display: 'flex', alignItems: 'center', gap: 4 }}>
                      <Sparkles size={12} /> {card.badge}
                    </span>
                  )}
                </div>
                <h3 style={{ fontSize: 18, marginBottom: 6 }}>{card.title}</h3>
                <p style={{ color: '#64748b', fontSize: 14, marginBottom: 16 }}>
                  {card.desc}
                </p>
                <div style={{ 
                  display: 'flex', alignItems: 'center', gap: 6, 
                  color: '#4f46e5', fontWeight: 500, fontSize: 14 
                }}>
                  Open <ArrowRight size={16} />
                </div>
              </div>
            </Link>
          ))}
      </div>
    </div>
  );
}