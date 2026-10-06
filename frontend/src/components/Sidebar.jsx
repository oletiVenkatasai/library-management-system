import { NavLink } from 'react-router-dom';
import { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';

function Sidebar() {
  const { user } = useContext(AuthContext);

  const adminNav = [
    {
      label: 'Overview',
      links: [
        { to: '/dashboard', label: 'Dashboard', icon: 'bi-speedometer2' },
      ],
    },
    {
      label: 'Catalogue',
      links: [
        { to: '/books',   label: 'Books',   icon: 'bi-journal-bookmark-fill' },
        { to: '/authors', label: 'Authors', icon: 'bi-person-lines-fill' },
      ],
    },
    {
      label: 'Circulation',
      links: [
        { to: '/members',      label: 'Members',       icon: 'bi-people-fill' },
        { to: '/borrow/issue', label: 'Issue Book',    icon: 'bi-box-arrow-right' },
        { to: '/borrowings',   label: 'Borrowings',    icon: 'bi-clock-history' },
        { to: '/overdue',      label: 'Overdue Books', icon: 'bi-exclamation-triangle-fill' },
      ],
    },
  ];

  return (
    <div className="sidebar d-flex flex-column" style={{ minHeight: 'calc(100vh - 56px)' }}>

      <div className="px-3 py-3 mb-1" style={{ borderBottom: '1px solid rgba(255,255,255,0.07)' }}>
        <div className="d-flex align-items-center gap-2">
          <div
            className="rounded-circle d-flex align-items-center justify-content-center flex-shrink-0"
            style={{
              width: 36, height: 36,
              background: 'linear-gradient(135deg,#4361ee,#4895ef)',
              fontSize: '1rem',
              color: '#fff',
            }}
          >
            <i className="bi bi-shield-lock-fill"></i>
          </div>
          <div style={{ overflow: 'hidden' }}>
            <div className="text-white fw-semibold text-truncate" style={{ fontSize: '0.85rem' }}>
              {user?.username}
            </div>
            <span className="badge bg-danger text-dark" style={{ fontSize: '0.65rem' }}>
              Administrator
            </span>
          </div>
        </div>
      </div>

      <div className="px-2 py-2 flex-grow-1">
        {adminNav.map((group) => (
          <div key={group.label}>
            <div className="sidebar-label">{group.label}</div>
            <ul className="nav flex-column mb-1">
              {group.links.map((link) => (
                <li className="nav-item mb-1" key={link.to}>
                  <NavLink
                    to={link.to}
                    className={({ isActive }) =>
                      `nav-link rounded d-flex align-items-center gap-2 ${isActive ? 'active' : ''}`
                    }
                  >
                    <i className={`bi ${link.icon}`} style={{ width: 18, textAlign: 'center' }}></i>
                    <span>{link.label}</span>
                  </NavLink>
                </li>
              ))}
            </ul>
            <div className="sidebar-divider"></div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default Sidebar;
